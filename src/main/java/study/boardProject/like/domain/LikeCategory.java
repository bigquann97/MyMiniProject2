package study.boardProject.like.domain;

import lombok.Getter;

@Getter
public enum LikeCategory {
    POST(Category.POST),  // 사용자 권한
    COMMENT(Category.COMMENT);  // 관리자 권한

    private final String category;

    LikeCategory(String category) {
        this.category = category;
    }

    public static class Category {
        public static final String POST = "CATEGORY_POST";
        public static final String COMMENT = "CATEGORY_COMMENT";
    }
}
