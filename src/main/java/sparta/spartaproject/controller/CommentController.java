package sparta.spartaproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.spartaproject.dto.CommentReq;
import sparta.spartaproject.dto.CommentRes;
import sparta.spartaproject.service.CommentService;
import sparta.spartaproject.service.ResultService;
import sparta.spartaproject.service.result.Result;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class CommentController {

    private final CommentService commentService;
    private final ResultService resultService;

    // 댓글 작성
    @PostMapping("/{postId}")
    public ResponseEntity<Result> writeComment(@PathVariable Long postId, @RequestBody CommentReq commentReq, HttpServletRequest request) {
        CommentRes commentRes = commentService.writeComment(postId, commentReq, request);
        Result result = resultService.getSuccessDataResult(150, "댓글작성", commentRes);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // 댓글 삭제
    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<Result> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, HttpServletRequest request) {
        commentService.deleteComment(postId, commentId, request);
        Result result = resultService.getSuccessResult(150, "댓글 삭제");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{postId}/{commentId}")
    public ResponseEntity<Result> modifyComment(@PathVariable Long postId, @PathVariable Long commentId, HttpServletRequest request, @RequestBody CommentReq commentReq) {
        CommentRes commentRes = commentService.modifyComment(postId, commentId, request, commentReq);
        Result result = resultService.getSuccessDataResult(150, "댓글 수정", commentRes);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
