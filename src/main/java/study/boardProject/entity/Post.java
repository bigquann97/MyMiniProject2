package study.boardProject.entity;

import lombok.*;
import study.boardProject.entity.common.TimeStamped;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Lob
    private String content;

    private String userLoginId; // Unique

    public void editPost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public boolean isWrittenByFindUser(User findUser) {
        return this.userLoginId.equals(findUser.getLoginId());
    }
}
