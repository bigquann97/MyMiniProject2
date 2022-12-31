package study.boardProject.service.post;

import org.springframework.data.domain.Pageable;
import study.boardProject.dto.post.PostRequest;
import study.boardProject.dto.post.PostResponse;
import study.boardProject.dto.post.PostSimpleResponse;
import study.boardProject.entity.User;

import java.util.List;

public interface PostService {

    public PostResponse getOnePost(Long postId);
    public List<PostSimpleResponse> getAllPosts();
    public List<PostSimpleResponse> findPagePost(Pageable pageable, int page);
    public void uploadPost(PostRequest postRequest, User user);
    public void modifyPost(Long id, PostRequest postRequest, User user);
    public void deletePostAndBelongs(Long postId, User user);

}
