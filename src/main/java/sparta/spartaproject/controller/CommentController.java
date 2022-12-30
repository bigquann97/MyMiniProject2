package sparta.spartaproject.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sparta.spartaproject.dto.comment.CommentRequest;
import sparta.spartaproject.dto.comment.CommentResponse;
import sparta.spartaproject.entity.User;
import sparta.spartaproject.exception.NotExistUserException;
import sparta.spartaproject.repository.UserRepository;
import sparta.spartaproject.result.Status;
import sparta.spartaproject.service.CommentService;

@Api(value = "댓글", tags = "댓글")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepository;

    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성합니다.")
    @PostMapping("/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse writeComment(@PathVariable Long postId, @RequestBody CommentRequest commentRequest) {
        User user = getPrincipal();
        return commentService.writeComment(postId, commentRequest, user);
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다.")
    @DeleteMapping("/{postId}/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        User user = getPrincipal();
        commentService.deleteComment(postId, commentId, user);
    }

    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정합니다.")
    @PutMapping("/{postId}/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {
        User user = getPrincipal();
        CommentResponse commentResponse = commentService.modifyComment(postId, commentId, commentRequest, user);
    }

    private User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByLoginId(authentication.getName()).orElseThrow(NotExistUserException::new);
    }

}
