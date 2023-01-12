package study.boardProject.user.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.boardProject.comment.dto.CommentResponse;
import study.boardProject.common.security.UserDetailsImpl;
import study.boardProject.post.dto.PostSimpleResponse;
import study.boardProject.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // 내가 쓴 글
    @ApiOperation(value = "내가 쓴 글", notes = "내가 쓴 글을 조회합니다.")
    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<PostSimpleResponse> getMyPosts(
            @RequestParam(defaultValue = "1") int page,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return userService.getMyPosts(page - 1, pageable, userDetails.getUser());
    }

    // 내가 쓴 댓글
    @ApiOperation(value = "내가 쓴 댓글", notes = "내가 쓴 댓글을 조회합니다.")
    @GetMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<CommentResponse> getMyComments(
            @RequestParam(defaultValue = "1") int page,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return userService.getMyComments(page - 1, pageable, userDetails.getUser());
    }

    // 내가 좋아요 누른 글
    @ApiOperation(value = "내가 좋아요 누른 글", notes = "내가 좋아요 누른 글을 조회합니다.")
    @GetMapping("/liked-posts")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<PostSimpleResponse> getMyLikePosts(
            @RequestParam(defaultValue = "1") int page,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return userService.getMyLikePosts(page - 1, pageable, userDetails.getUser());
    }

    // 내가 좋아요 누른 댓글
    @ApiOperation(value = "내가 좋아요 누른 댓글", notes = "내가 좋아요 누른 댓글을 조회합니다.")
    @GetMapping("/liked-comments")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<CommentResponse> getMyLikeComments(
            @RequestParam(defaultValue = "1") int page,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return userService.getMyLikeComments(page - 1, pageable, userDetails.getUser());
    }

    @DeleteMapping("/deletion")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteAccount(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        userService.deleteUser(userDetails.getUser());
    }
}
