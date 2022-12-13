package sparta.spartaproject.controller.post;

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
import sparta.spartaproject.dto.post.PostDto;
import sparta.spartaproject.entity.user.User;
import sparta.spartaproject.exception.NotExistUserException;
import sparta.spartaproject.repository.user.UserRepository;
import sparta.spartaproject.service.post.PostService;
import sparta.spartaproject.service.result.ResultService;
import sparta.spartaproject.result.Result;
import sparta.spartaproject.result.Status;

import java.util.List;

@Api(value = "게시글 관련 API", tags = "게시글 관련 API")
@RequestMapping("/api/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final ResultService resultService;
    private final UserRepository userRepository;

    @ApiOperation(value = "게시글 단건 조회", notes = "단건 게시글을 조회합니다.")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result getOnePost(@PathVariable Long id) {
        PostDto.PostRes post = postService.getOnePost(id);
        Result result = resultService.getSuccessDataResult(Status.S_POST_VIEW.getCode(), Status.S_POST_VIEW.getMsg(), post);
        return result;
    }

    @ApiOperation(value = "게시글 전체 조회", notes = "전체 게시글을 조회합니다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Result getAllPosts() {
        List<PostDto.PostRes> posts = postService.getAllPosts();
        Result result = resultService.getSuccessDataResult(Status.S_POST_VIEW.getCode(), Status.S_POST_VIEW.getMsg(), posts);
        return result;
    }

    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result uploadPost(@RequestBody PostDto.PostReq postReq) {
        User user = getPrincipal();
        postService.uploadPost(postReq, user);
        Result result = resultService.getSuccessResult(Status.S_POST_UPLOAD.getCode(), Status.S_POST_UPLOAD.getMsg());
        return result;
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 삭제합니다.")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result modifyPost(@PathVariable Long id, @RequestBody PostDto.PostReq postReq) {
        User user = getPrincipal();
        PostDto.PostRes modifiedPost = postService.modifyPost(id, postReq, user);
        Result result = resultService.getSuccessDataResult(Status.S_POST_MODIFY.getCode(), Status.S_POST_MODIFY.getMsg(), modifiedPost);
        return result;
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result deletePost(@PathVariable Long id) {
        User user = getPrincipal();
        postService.deletePost(id, user);
        Result result = resultService.getSuccessResult(Status.S_POST_DELETE.getCode(), Status.S_POST_DELETE.getMsg());
        return result;
    }

    @ApiOperation(value = "게시글 페이징 조회", notes = "게시글을 페이징 조회합니다.")
    @GetMapping("/temp")
    @ResponseStatus(HttpStatus.OK)
    public Result showPagePost(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<PostDto.PostSimpleRes> pagePost = postService.findPagePost(pageable);
        Result result = resultService.getSuccessDataResult(100, "성공", pagePost);
        return result;
    }

    private User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByLoginId(authentication.getName()).orElseThrow(NotExistUserException::new);
    }
}
