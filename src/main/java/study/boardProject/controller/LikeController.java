package study.boardProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.boardProject.common.config.auth.UserDetailsImpl;
import study.boardProject.service.like.LikeService;

@RequestMapping("/api/likes")
@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/post")
    public void addLikeOnPost(
            @RequestParam Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        likeService.addLikeOnPost(id, userDetails.getUser());
    }

    @DeleteMapping("/post")
    public void cancelLikeOnPost(
            @RequestParam Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        likeService.cancelLikeOnPost(id, userDetails.getUser());
    }

    @PostMapping("/comment")
    public void addLikeOnComment(
            @RequestParam Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        likeService.addLikeOnComment(id, userDetails.getUser());
    }

    @DeleteMapping("/comment")
    public void cancelLikeOnComment(
            @RequestParam Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        likeService.cancelLikeOnComment(id, userDetails.getUser());
    }


}
