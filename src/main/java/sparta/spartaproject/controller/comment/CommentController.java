package sparta.spartaproject.controller.comment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sparta.spartaproject.dto.comment.CommentDto;
import sparta.spartaproject.entity.user.User;
import sparta.spartaproject.exception.NotExistUserException;
import sparta.spartaproject.repository.user.UserRepository;
import sparta.spartaproject.result.Result;
import sparta.spartaproject.result.ResultService;
import sparta.spartaproject.result.Status;
import sparta.spartaproject.service.comment.CommentService;

@Api(value = "댓글", tags = "댓글")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final ResultService resultService;
    private final UserRepository userRepository;

    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성합니다.")
    @PostMapping("/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Result writeComment(@PathVariable Long postId, @RequestBody CommentDto.CommentReq commentReq) {
        User user = getPrincipal();
        CommentDto.CommentRes commentRes = commentService.writeComment(postId, commentReq, user);
        return resultService.getSuccessDataResult(Status.S_COMMENT_UPLOAD, commentRes);
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다.")
    @DeleteMapping("/{postId}/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        User user = getPrincipal();
        commentService.deleteComment(postId, commentId, user);
        return resultService.getSuccessResult(Status.S_COMMENT_DELETE);
    }

    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정합니다.")
    @PutMapping("/{postId}/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public Result modifyComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentDto.CommentReq commentReq) {
        User user = getPrincipal();
        CommentDto.CommentRes commentRes = commentService.modifyComment(postId, commentId, commentReq, user);
        return resultService.getSuccessDataResult(Status.S_COMMENT_MODIFY, commentRes);
    }

    private User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByLoginId(authentication.getName()).orElseThrow(NotExistUserException::new);
    }
}
