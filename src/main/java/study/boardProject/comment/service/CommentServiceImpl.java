package study.boardProject.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.boardProject.auth.entity.User;
import study.boardProject.comment.dto.CommentRequest;
import study.boardProject.comment.entity.Comment;
import study.boardProject.comment.repository.CommentRepository;
import study.boardProject.common.exception.CommentException;
import study.boardProject.common.exception.PostException;
import study.boardProject.like.repository.LikeRepository;
import study.boardProject.post.entity.Post;
import study.boardProject.post.repository.PostRepository;

import java.util.List;

import static study.boardProject.common.exception.CommentException.*;
import static study.boardProject.common.exception.CommentException.CommentNotFoundException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public void writeComment(Long postId, CommentRequest commentRequest, User user) {
        Post post = postRepository.findById(postId).orElseThrow(PostException.PostNotFoundException::new);
        Comment comment = commentRequest.toEntity(post, user);
        commentRepository.save(comment);
    }

    @Override
    public void writeReply(Long parentId, CommentRequest commentRequest, User user) {
        Comment parent = commentRepository.findById(parentId).orElseThrow(CommentNotFoundException::new);
        validateIfParentIsAlreadyReply(parent);
        Post post = parent.getPost();
        Comment reply = commentRequest.toEntity(post, user, parent);
        commentRepository.save(reply);
    }

    @Override
    @Transactional
    public void modifyComment(Long commentId, CommentRequest commentRequest, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        comment.validateUser(user);
        comment.editComment(commentRequest.getContent());
        commentRepository.saveAndFlush(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        comment.validateUser(user);
        deleteCommentAndBelongs(comment);
    }

    @Override
    @Transactional
    public void deleteCommentAndBelongs(Comment comment) {
        List<Comment> replies = commentRepository.findByParent(comment); // 댓글에 달린 모든 대댓글 조회
        likeRepository.deleteByCommentIn(replies); // 대댓글의 좋아요 삭제
        likeRepository.deleteByComment(comment); // 댓글의 좋아요 삭제
        commentRepository.deleteAll(replies); // 대댓글 삭제
        commentRepository.delete(comment); // 댓글 삭제
    }

    private void validateIfParentIsAlreadyReply(Comment parent) {
        if(parent.isReply())
            throw new CannotReplyException();
    }

}
