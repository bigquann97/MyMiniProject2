package study.boardProject.factory;

import study.boardProject.auth.entity.User;
import study.boardProject.post.entity.Post;

public class PostFactory {

    public static Post buildPost() {
        return Post.builder()
                .user(UserFactory.buildUser1())
                .title("title")
                .content("content")
                .build();
    }

    public static Post buildPostByUser(User user) {
        return Post.builder()
                .user(user)
                .title("title")
                .content("content")
                .build();
    }

}
