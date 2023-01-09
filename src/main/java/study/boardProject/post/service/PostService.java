package study.boardProject.post.service;

import org.springframework.data.domain.Pageable;
import study.boardProject.auth.entity.User;
import study.boardProject.post.dto.PostRequest;
import study.boardProject.post.dto.PostResponse;
import study.boardProject.post.dto.PostSimpleResponse;

import java.util.List;

public interface PostService {

    PostResponse getOnePost(Long postId);

    List<PostSimpleResponse> findPagePost(Pageable pageable, int page);

    void uploadPost(PostRequest postRequest, User user);

    void modifyPost(Long id, PostRequest postRequest, User user);

    void deletePostAndBelongs(Long postId, User user);

}
