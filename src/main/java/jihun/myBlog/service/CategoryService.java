package jihun.myBlog.service;

import jihun.myBlog.controller.dto.CategoryCreateForm;
import jihun.myBlog.controller.dto.CategoryEditForm;
import jihun.myBlog.controller.dto.CategoryListForm;
import jihun.myBlog.entity.Category;
import jihun.myBlog.exception.CustomException;
import jihun.myBlog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static jihun.myBlog.exception.ErrorCode.CATEGORY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findCategories() {
        return categoryRepository.findAll();
    }

    public Long saveCategory(CategoryCreateForm form) {

        Category category = Category.builder()
                .name(form.getName())
                .description(form.getDescription())
                .build();

        return categoryRepository.save(category).getId();
    }

    public Category findCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new CustomException(CATEGORY_NOT_FOUND)
        );
    }

    @Transactional
    public void updateCategory(Long id, CategoryEditForm form) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CustomException(CATEGORY_NOT_FOUND)
        );
        category.updateCategory(form);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }


    public List<CategoryListForm> getCategoryListForms() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryListForm> categoryListForms = new ArrayList<>();

        for (Category category : categories) {
            CategoryListForm categoryListForm = CategoryListForm.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .build();
            categoryListForms.add(categoryListForm);
        }

        return categoryListForms;
    }
}
