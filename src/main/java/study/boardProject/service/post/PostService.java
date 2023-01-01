package study.boardProject.service.post;

import org.springframework.data.domain.Pageable;
import study.boardProject.dto.post.PostRequest;
import study.boardProject.dto.post.PostResponse;
import study.boardProject.dto.post.PostSimpleResponse;
import study.boardProject.entity.User;

import java.util.List;

public interface PostService {

    PostResponse getOnePost(Long postId);

    List<PostSimpleResponse> getAllPosts();

    List<PostSimpleResponse> findPagePost(Pageable pageable, int page);

    void uploadPost(PostRequest postRequest, User user);

    void modifyPost(Long id, PostRequest postRequest, User user);

    void deletePostAndBelongs(Long postId, User user);

}
