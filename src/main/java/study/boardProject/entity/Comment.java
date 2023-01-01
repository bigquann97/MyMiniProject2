package study.boardProject.entity;

import lombok.*;
import study.boardProject.entity.common.TimeStamp;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String userLoginId; // User 에 대한 unique

    private Long postId; // Post에 대한 unique // fk값으로 하나르 ㄹ들고있어요

    private AtomicLong likeCount;

    public void editComment(String content) {
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
