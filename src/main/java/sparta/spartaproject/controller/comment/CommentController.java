package sparta.spartaproject.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.spartaproject.dto.CommentReq;
import sparta.spartaproject.dto.CommentRes;
import sparta.spartaproject.service.comment.CommentService;
import sparta.spartaproject.service.result.ResultService;
import sparta.spartaproject.result.Result;
import sparta.spartaproject.result.Status;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final ResultService resultService;

    @PostMapping("/{postId}")
    public ResponseEntity<Result> writeComment(@PathVariable Long postId, @RequestBody CommentReq commentReq, HttpServletRequest request) {
        CommentRes commentRes = commentService.writeComment(postId, commentReq, request);
        Result result = resultService.getSuccessDataResult(Status.S_COMMENT_UPLOAD.getCode(), Status.S_COMMENT_UPLOAD.getMsg(), commentRes);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<Result> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, HttpServletRequest request) {
        commentService.deleteComment(postId, commentId, request);
        Result result = resultService.getSuccessResult(Status.S_COMMENT_DELETE.getCode(), Status.S_COMMENT_DELETE.getMsg());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{postId}/{commentId}")
    public ResponseEntity<Result> modifyComment(@PathVariable Long postId, @PathVariable Long commentId, HttpServletRequest request, @RequestBody CommentReq commentReq) {
        CommentRes commentRes = commentService.modifyComment(postId, commentId, request, commentReq);
        Result result = resultService.getSuccessDataResult(Status.S_COMMENT_MODIFY.getCode(), Status.S_COMMENT_MODIFY.getMsg(), commentRes);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
