package study.boardProject.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.boardProject.category.dto.CategoryRequest;
import study.boardProject.category.service.CategoryService;

@RequestMapping("/api/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public void getAllCategories() {
        categoryService.getAllCategories();
    }

    // 카테고리 생성
    @PostMapping
    public void makeCategory(
            @RequestBody CategoryRequest request
    ) {
        categoryService.makeCategory(request);
    }

    // 자식 카테고리 생성
    @PostMapping("/{category-id}")
    public void makeChildCategory(
            @PathVariable("category-id") Long id,
            @RequestBody CategoryRequest request
    ) {
        categoryService.makeChildCategory(id, request);
    }

    // 카테고리 삭제
    @DeleteMapping("/{category-id}")
    public void deleteCategory(
            @PathVariable("category-id") Long id
    ) {
        categoryService.deleteCategory(id);
    }

}
