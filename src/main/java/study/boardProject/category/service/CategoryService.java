package study.boardProject.category.service;

import study.boardProject.category.dto.CategoryRequest;

public interface CategoryService {
    void makeCategory(CategoryRequest request);

    void makeChildCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);
}
