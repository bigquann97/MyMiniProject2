package sparta.spartaproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.spartaproject.dto.PostRequestDto;
import sparta.spartaproject.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/api/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public void getOnePost(@PathVariable Long id) {
        postService.getOnePost(id);

//        return ResponseEntity.status(HttpStatus.OK).body();
    }

    @GetMapping
    public void getAllPosts() {
        postService.getAllPosts();
//        return ResponseEntity.status(HttpStatus.OK).body();
    }

    @PostMapping
    public void uploadPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {

//        return ResponseEntity.status(HttpStatus.OK).body();
    }

    @PutMapping("/{id}")
    public void modifyPost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {

//        return ResponseEntity.status(HttpStatus.OK).body();
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id, HttpServletRequest request) {

//        return ResponseEntity.status(HttpStatus.OK).body();
    }

}
