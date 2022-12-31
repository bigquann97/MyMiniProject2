package study.boardProject.entity;

import lombok.*;
import study.boardProject.entity.common.TimeStamped;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String userLoginId; // User 에 대한 unique

    private Long postId; // Post에 대한 unique // fk값으로 하나르 ㄹ들고있어요

    public void editComment(String content) {
        this.content = content;
    }

    public boolean isBelongToPost(Post post) {
        return this.postId.equals(post.getId());
    }

    public boolean isWrittenByFindUser(User findUser) {
        return this.userLoginId.equals(findUser.getLoginId());
    }
}
