package sparta.spartaproject.entity;

import lombok.*;
import sparta.spartaproject.dto.PostReq;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments = new ArrayList<>();

    public static Post of(PostReq uploadPostReq, User user) {
        return Post.builder()
                .title(uploadPostReq.getTitle())
                .content(uploadPostReq.getContent())
                .user(user)
                .build();
    }

    public void editPost(PostReq postReq) {
        this.title = postReq.getTitle();
        this.content = postReq.getContent();
    }

    public boolean hashThisComment(Comment targetComment) {
        for (Comment comment : comments) {
            if (comment.equals(targetComment)) {
                return true;
            }
        }
        return false;
    }
}
