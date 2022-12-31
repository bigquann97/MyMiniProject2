package sparta.spartaproject.service.post;

import org.springframework.data.domain.Pageable;
import sparta.spartaproject.dto.post.PostRequest;
import sparta.spartaproject.dto.post.PostResponse;
import sparta.spartaproject.dto.post.PostSimpleResponse;
import sparta.spartaproject.entity.User;

import java.util.List;

public interface PostService {

    public PostResponse getOnePost(Long postId);
    public List<PostSimpleResponse> getAllPosts();
    public List<PostSimpleResponse> findPagePost(Pageable pageable, int page);
    public void uploadPost(PostRequest postRequest, User user);
    public void modifyPost(Long id, PostRequest postRequest, User user);
    public void deletePostAndBelongs(Long postId, User user);

}
