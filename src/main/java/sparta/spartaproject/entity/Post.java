package sparta.spartaproject.entity;

import lombok.*;
import sparta.spartaproject.dto.post.PostRequest;

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

    @OneToMany(mappedBy = "post",
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    private List<Comment> comments = new ArrayList<>();

    public void editPost(PostRequest postRequest) {
        this.title = postRequest.getTitle();
        this.content = postRequest.getContent();
    }

    public boolean hasComment(Comment targetComment) {
        return comments.stream().anyMatch(x -> x.equals(targetComment));
    }

}
