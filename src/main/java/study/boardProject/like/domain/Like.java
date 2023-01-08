package study.boardProject.like.domain;

import lombok.*;
import study.boardProject.common.domain.TimeStamp;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes")
public class Like extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long targetId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LikeCategory category;
}
