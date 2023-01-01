package study.boardProject.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;
import study.boardProject.entity.common.TimeStamp;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicLong;

@Builder
@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Lob
    private String content;

    private String userLoginId; // Unique

    private AtomicLong likeCount;

    public void editPost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addLikeCount() {
        if(likeCount.get() < 0L || likeCount.get() >= Long.MAX_VALUE)
            throw new NumberFormatException();
        else
            this.likeCount.incrementAndGet();
    }

    public void minusLikeCount() {
        if(likeCount.get() < 0L || likeCount.get() >= Long.MAX_VALUE)
            throw new NumberFormatException();
        else
            this.likeCount.decrementAndGet();
    }
}
