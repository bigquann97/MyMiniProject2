package study.boardProject.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.boardProject.config.auth.UserDetailsImpl;
import study.boardProject.dto.post.PostRequest;
import study.boardProject.dto.post.PostResponse;
import study.boardProject.dto.post.PostSimpleResponse;
import study.boardProject.service.post.PostService;

import java.util.List;

@Api(value = "게시글 관련 API", tags = "게시글 관련 API")
@RequestMapping("/api/posts")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "게시글 단건 조회", notes = "단건 게시글을 조회합니다.")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse getOnePost(@PathVariable Long id) {
        return postService.getOnePost(id);
    }

    @ApiOperation(value = "게시글 페이징 조회", notes = "게시글을 페이징 조회합니다.")
    @GetMapping // TODO: 2022/12/31 PAGE API URL
    @ResponseStatus(HttpStatus.OK)
    public List<PostSimpleResponse> showPagePost(
            @RequestParam(defaultValue = "0") int page,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.findPagePost(pageable, page);
    }

    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void uploadPost(
            @RequestBody PostRequest postRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        postService.uploadPost(postRequest, userDetails.getUser());
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 삭제합니다.")
    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void modifyPost(
            @PathVariable Long postId,
            @RequestBody PostRequest postRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        postService.modifyPost(postId, postRequest, userDetails.getUser());
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        postService.deletePostAndBelongs(postId, userDetails.getUser());
    }

}
