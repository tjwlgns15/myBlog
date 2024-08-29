package jihun.myBlog.service;

import jihun.myBlog.dto.category.CategoryResponse;
import jihun.myBlog.dto.category.CreateCategoryRequest;
import jihun.myBlog.dto.category.EditCategoryRequest;
import jihun.myBlog.entity.Category;
import jihun.myBlog.global.exception.CustomException;
import jihun.myBlog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static jihun.myBlog.global.exception.ErrorCode.CATEGORY_ALREADY_EXISTS;
import static jihun.myBlog.global.exception.ErrorCode.CATEGORY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Long saveCategory(CreateCategoryRequest form) {

        if (categoryRepository.existsByName(form.getName())) {
            throw new CustomException(CATEGORY_ALREADY_EXISTS);
        }

        Category category = Category.builder()
                .name(form.getName())
                .build();
        return categoryRepository.save(category).getId();
    }

    public List<CategoryResponse> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .collect(Collectors.toList());
    }

    // 쓸 일 있나?
    public CategoryResponse findCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CustomException(CATEGORY_NOT_FOUND)
        );

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    @Transactional
    public void updateCategory(Long id, EditCategoryRequest form) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CustomException(CATEGORY_NOT_FOUND)
        );
        // 본인 제외 중복 체크
        if (!category.getName().equals(form.getName()) && categoryRepository.existsByName(form.getName())) {
            throw new CustomException(CATEGORY_ALREADY_EXISTS);
        }


        category.updateCategory(form);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
