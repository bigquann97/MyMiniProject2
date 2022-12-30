package sparta.spartaproject.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sparta.spartaproject.dto.post.PostRequest;
import sparta.spartaproject.dto.post.PostResponse;
import sparta.spartaproject.dto.post.PostSimpleResponse;
import sparta.spartaproject.entity.User;
import sparta.spartaproject.exception.NotExistUserException;
import sparta.spartaproject.repository.UserRepository;
import sparta.spartaproject.result.Status;
import sparta.spartaproject.service.PostService;

import java.util.List;

@Api(value = "게시글 관련 API", tags = "게시글 관련 API")
@RequestMapping("/api/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    @ApiOperation(value = "게시글 단건 조회", notes = "단건 게시글을 조회합니다.")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse getOnePost(@PathVariable Long id) {
        return postService.getOnePost(id);
    }

    @ApiOperation(value = "게시글 전체 조회", notes = "전체 게시글을 조회합니다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadPost(@RequestBody PostRequest postRequest) {
        User user = getPrincipal();
        postService.uploadPost(postRequest, user);
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 삭제합니다.")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyPost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
        User user = getPrincipal();
        PostResponse modifiedPost = postService.modifyPost(id, postRequest, user);
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable Long id) {
        User user = getPrincipal();
        postService.deletePost(id, user);
    }

    @ApiOperation(value = "게시글 페이징 조회", notes = "게시글을 페이징 조회합니다.")
    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public List<PostSimpleResponse> showPagePost(
            @PageableDefault(size = 5,
                    sort = "id",
                    direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.findPagePost(pageable);
    }

    private User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByLoginId(authentication.getName()).orElseThrow(NotExistUserException::new);
    }

}
