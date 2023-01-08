package study.boardProject.user.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.boardProject.common.security.UserDetailsImpl;
import study.boardProject.comment.dto.CommentResponse;
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
    @GetMapping("/my-posts")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<PostSimpleResponse> getMyPosts(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return userService.getMyPosts(userDetails.getUser());
    }

    // 내가 쓴 댓글
    @ApiOperation(value = "내가 쓴 댓글", notes = "내가 쓴 댓글을 조회합니다.")
    @GetMapping("/my-comments")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<CommentResponse> getMyComments(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return userService.getMyComments(userDetails.getUser());
    }


    // 내가 좋아요 누른 글
    @ApiOperation(value = "내가 좋아요 누른 글", notes = "내가 좋아요 누른 글을 조회합니다.")
    @GetMapping("/my-like-posts")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<PostSimpleResponse> getMyLikePosts(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return userService.getMyLikePosts(userDetails.getUser());
    }


    // 내가 좋아요 누른 댓글
    @ApiOperation(value = "내가 좋아요 누른 댓글", notes = "내가 좋아요 누른 댓글을 조회합니다.")
    @GetMapping("/my-like-comments")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<CommentResponse> getMyLikeComments(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return userService.getMyLikeComments(userDetails.getUser());
    }

}
