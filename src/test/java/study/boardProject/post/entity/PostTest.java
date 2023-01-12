package study.boardProject.post.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.boardProject.auth.entity.User;
import study.boardProject.common.exception.AuthException;
import study.boardProject.factory.PostFactory;
import study.boardProject.factory.UserFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostTest {

    @DisplayName("1. 게시글 빌더 테스트")
    @Test
    void test_1() {
        //given
        User user = UserFactory.buildUser1();
        String title = "title";
        String content = "content";

        //when
        Post post = Post.builder()
                .title(title)
                .user(user)
                .content(content)
                .build();

        //then
        assertThat(post.getId()).isNull();
        assertThat(post.getUser()).isEqualTo(user);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

    @DisplayName("2. 게시글 수정 테스트")
    @Test
    void test_2() {
        //given
        Post post = PostFactory.buildPost();
        String editTitle = "editTitle";
        String editContent = "editContent";

        //when
        post.editPost(editTitle, editContent);

        //then
        assertThat(post.getTitle()).isEqualTo(editTitle);
        assertThat(post.getContent()).isEqualTo(editContent);
    }

    @DisplayName("3. 접근 유저 유효성 테스트")
    @Test
    void test_3 () {
        User user1 = UserFactory.buildUser1();
        User user2 = UserFactory.buildUser2();
        Post post = PostFactory.buildPostByUser(user1);

        Assertions.assertThatNoException().isThrownBy(() -> post.validateUser(user1));
        assertThatThrownBy(() -> post.validateUser(user2)).isInstanceOf(AuthException.AuthenticationException.class);
    }

}