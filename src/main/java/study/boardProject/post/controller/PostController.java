package study.boardProject.post.controller;

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
import study.boardProject.common.security.UserDetailsImpl;
import study.boardProject.post.dto.PostRequest;
import study.boardProject.post.dto.PostResponse;
import study.boardProject.post.dto.PostSimpleResponse;
import study.boardProject.post.service.PostService;

import java.util.List;

@Api(value = "게시글 관련 API", tags = "게시글 관련 API")
@RequestMapping("/api/posts")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

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

    // /api/posts/1
    @ApiOperation(value = "게시글 단건 조회", notes = "단건 게시글을 조회합니다.")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse getOnePost(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int page
    ) {
        return postService.getOnePost(id, page - 1);
    }

    // /api/posts?page=1
    @ApiOperation(value = "게시글 페이징 조회", notes = "게시글을 페이징 조회합니다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<PostSimpleResponse> showPagePost(
            @RequestParam(defaultValue = "1") int page,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.findPagePost(pageable, page - 1);
    }

    // /api/posts/1
    @ApiOperation(value = "게시글 수정", notes = "게시글을 삭제합니다.")
    @PatchMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void modifyPost(
            @PathVariable Long postId,
            @RequestBody PostRequest postRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        postService.modifyPost(postId, postRequest, userDetails.getUser());
    }

    // /api/posts/1
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        postService.deletePost(postId, userDetails.getUser());
    }

}
