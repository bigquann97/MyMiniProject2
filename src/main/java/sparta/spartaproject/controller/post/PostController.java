package sparta.spartaproject.controller.post;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sparta.spartaproject.dto.PostReq;
import sparta.spartaproject.dto.PostRes;
import sparta.spartaproject.entity.user.User;
import sparta.spartaproject.exception.NotExistUserException;
import sparta.spartaproject.repository.user.UserRepository;
import sparta.spartaproject.service.post.PostService;
import sparta.spartaproject.service.result.ResultService;
import sparta.spartaproject.result.Result;
import sparta.spartaproject.result.Status;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "게시글 관련 API", tags = "게시글 관련 API")
@RequestMapping("/api/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final ResultService resultService;
    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Result> getOnePost(@PathVariable Long id) {
        PostRes post = postService.getOnePost(id);
        Result result = resultService.getSuccessDataResult(Status.S_POST_VIEW.getCode(), Status.S_POST_VIEW.getMsg(), post);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping
    public ResponseEntity<Result> getAllPosts() {
        List<PostRes> posts = postService.getAllPosts();
        Result result = resultService.getSuccessDataResult(Status.S_POST_VIEW.getCode(), Status.S_POST_VIEW.getMsg(), posts);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Result> uploadPost(@RequestBody PostReq postReq) {
        // http://localhost:8080/api/post
        User user = getPrincipal();
        postService.uploadPost(postReq, user);
        Result result = resultService.getSuccessResult(Status.S_POST_UPLOAD.getCode(), Status.S_POST_UPLOAD.getMsg());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> modifyPost(@PathVariable Long id, @RequestBody PostReq postReq, HttpServletRequest request) {
        PostRes modifiedPost = postService.modifyPost(id, postReq, request);
        Result result = resultService.getSuccessDataResult(Status.S_POST_MODIFY.getCode(), Status.S_POST_MODIFY.getMsg(), modifiedPost);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deletePost(@PathVariable Long id, HttpServletRequest request) {
        postService.deletePost(id, request);
        Result result = resultService.getSuccessResult(Status.S_POST_DELETE.getCode(), Status.S_POST_DELETE.getMsg());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    private User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByLoginId(authentication.getName()).orElseThrow(NotExistUserException::new);
        return user;
    }

}
