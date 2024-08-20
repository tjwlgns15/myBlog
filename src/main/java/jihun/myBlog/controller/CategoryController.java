package jihun.myBlog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jihun.myBlog.controller.dto.*;
import jihun.myBlog.entity.Category;
import jihun.myBlog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public List<CategoryListForm> listForm() {
        return categoryService.getCategoryListForms();
    }

    @GetMapping("/{id}")
    public CategoryDetailForm detailForm(@PathVariable Long id) {
        Category category = categoryService.findCategory(id);

        return CategoryDetailForm.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    @PostMapping("/new")
    public ResponseEntity<?> create(@Valid @ModelAttribute CategoryCreateForm form,
                                    BindingResult result
    ) {
        log.info("Form data: {}", form);

        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }

        Long categoryId = categoryService.saveCategory(form);
        return ResponseEntity.ok("카테고리 등록이 완료되었습니다. ID: " + categoryId);
    }




    @GetMapping("/{id}/edit")
    public CategoryEditForm editForm(@PathVariable Long id, Model model) {
        Category category = categoryService.findCategory(id);
        return CategoryEditForm.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @ModelAttribute CategoryEditForm form, BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }

        categoryService.updateCategory(id, form);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

}
