package sparta.spartaproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.spartaproject.dto.PostReq;
import sparta.spartaproject.dto.PostRes;
import sparta.spartaproject.service.PostService;
import sparta.spartaproject.service.ResultService;
import sparta.spartaproject.service.result.Result;
import sparta.spartaproject.service.result.Status;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/api/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final ResultService resultService;

    @GetMapping("/{id}")
    public ResponseEntity<Result> getOnePost(@PathVariable Long id) {
        PostRes post = postService.getOnePost(id);
        Result result = resultService.getSuccessDataResult(Status.S_POST_VIEW.getCode(), Status.S_POST_VIEW.getMsg(), post);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping
    public ResponseEntity<Result> getAllPosts() {
        List<PostRes> posts = postService.getAllPosts();
        Result result = resultService.getSuccessDataResult(Status.S_POST_VIEW.getCode(), Status.S_POST_VIEW.getMsg(), posts);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    public ResponseEntity<Result> uploadPost(@RequestBody PostReq postReq, HttpServletRequest request) {
        postService.uploadPost(postReq, request);
        Result result = resultService.getSuccessResult(Status.S_POST_UPLOAD.getCode(), Status.S_POST_UPLOAD.getMsg());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> modifyPost(@PathVariable Long id, @RequestBody PostReq postReq, HttpServletRequest request) {
        PostRes modifiedPost = postService.modifyPost(id, postReq, request);
        Result result = resultService.getSuccessDataResult(Status.S_POST_MODIFY.getCode(), Status.S_POST_MODIFY.getMsg(), modifiedPost);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deletePost(@PathVariable Long id, HttpServletRequest request) {
        postService.deletePost(id, request);
        Result result = resultService.getSuccessResult(Status.S_POST_DELETE.getCode(), Status.S_POST_DELETE.getMsg());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
