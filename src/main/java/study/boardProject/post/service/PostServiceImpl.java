package study.boardProject.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.boardProject.auth.entity.User;
import study.boardProject.comment.entity.Comment;
import study.boardProject.comment.repository.CommentRepository;
import study.boardProject.comment.service.CommentService;
import study.boardProject.like.repository.LikeRepository;
import study.boardProject.post.dto.PostRequest;
import study.boardProject.post.dto.PostResponse;
import study.boardProject.post.dto.PostSimpleResponse;
import study.boardProject.post.entity.Post;
import study.boardProject.post.repository.PostRepository;

import java.util.*;

import static study.boardProject.common.exception.PostException.PostNotFoundException;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    private final CommentService commentService;

    @Override
    @Transactional
    public void uploadPost(PostRequest postRequest, User user) {
        Post post = postRequest.toEntity(user);
        postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getOnePost(Long postId, int page) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        long postLikeCount = likeRepository.countByPost(post);

        List<Comment> comments = commentRepository.findByPost(post);
        List<Comment> pagedComments = pagingComments(comments, page);

        Map<Comment, Long> commentMap = new LinkedHashMap<>();

        for (Comment comment : pagedComments) {
            long commentLikeCount = likeRepository.countByComment(comment);
            commentMap.put(comment, commentLikeCount);
        }

        return PostResponse.of(post, postLikeCount, commentMap);
    }

    @Override
    @Transactional
    public List<PostSimpleResponse> findPagePost(Pageable pageable, int page) {
        List<PostSimpleResponse> result = new ArrayList<>();
        Page<Post> pages = postRepository.findAll(pageable.withPage(page));
        for (Post post : pages) {
            long likeCount = likeRepository.countByPost(post);
            PostSimpleResponse element = PostSimpleResponse.of(post, likeCount);
            result.add(element);
        }
        return result;
    }

    @Override
    @Transactional
    public void modifyPost(Long postId, PostRequest postRequest, User user) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        post.validateUser(user);
        post.editPost(postRequest.getTitle(), postRequest.getContent());
        postRepository.saveAndFlush(post);
    }

    @Override
    @Transactional
    public void deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        post.validateUser(user);
        deletePostAndBelongs(post);
    }

    @Override
    @Transactional
    public void deletePostAndBelongs(Post post) {
        List<Comment> comments = commentRepository.findByPost(post); // 게시글 관련 모든 댓글 찾아오기
        for (Comment comment : comments) { // 게시글에 달린 모든 댓글 삭제
            commentService.deleteCommentAndBelongs(comment);
        }
        likeRepository.deleteByPost(post); // 게시글에 달린 모든 좋아요 삭제
        postRepository.delete(post); // 최종 게시물 삭제
    }

    private List<Comment> pagingComments(List<Comment> comments, int page) {
        LinkedList<Comment> pagedComments = new LinkedList<>();

        for (int i = 0; i <= page; i++) {

            total:
            for (Comment comment : comments) {
                if(!comment.isReply()) {
                    pagedComments.add(comment);

                    if(pagedComments.size() == 10) break;

                    for (Comment reply : comments) {
                        if(reply.isReply()) {
                            if (reply.getParent().getId().equals(comment.getId())) {
                                pagedComments.add(reply);

                                if(pagedComments.size() == 10) break total;
                            }
                        }
                    }

                } else {
                    pagedComments.add(comment);
                    if(pagedComments.size() == 10) break;
                }
            }

            if (i != page) {
                comments.removeAll(pagedComments);
                pagedComments.removeAll(pagedComments);
            }

        }


        return pagedComments;
    }


}
