package study.boardProject.comment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.boardProject.common.security.UserDetailsImpl;
import study.boardProject.comment.dto.CommentRequest;
import study.boardProject.comment.service.CommentService;

@Api(value = "댓글", tags = "댓글")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    // /api/comments?postId=3
    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void writeComment(
            @RequestParam Long postId,
            @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.writeComment(postId, commentRequest, userDetails.getUser());
    }

    // /api/comments/4
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.deleteCommentAndBelongs(commentId, userDetails.getUser());
    }

    // /api/comments/4
    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정합니다.")
    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void modifyComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.modifyComment(commentId, commentRequest, userDetails.getUser());
    }

}
