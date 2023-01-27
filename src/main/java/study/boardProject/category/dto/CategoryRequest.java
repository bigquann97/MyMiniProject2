package study.boardProject.category.dto;

import lombok.Getter;
import study.boardProject.category.entity.Category;

@Getter
public class CategoryRequest {

    String name;

    public Category toEntity() {
        return Category.builder()
                .name(this.name)
                .build();
    }

    public Category toEntity(Category parent) {
        return Category.builder()
                .name(this.name)
                .parentCategory(parent)
                .build();
    }

}
