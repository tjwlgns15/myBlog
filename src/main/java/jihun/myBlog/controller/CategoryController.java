package jihun.myBlog.controller;

import jakarta.validation.Valid;
import jihun.myBlog.dto.category.CreateCategoryRequest;
import jihun.myBlog.dto.category.EditCategoryRequest;
import jihun.myBlog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/new")
    public ResponseEntity<?> getCreateCategoryForm() {
        return ResponseEntity.ok(new CreateCategoryRequest());
    }

    @PostMapping("/new")
    public ResponseEntity<?> createCategory(@Valid @ModelAttribute CreateCategoryRequest form, BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        Long categoryId = categoryService.saveCategory(form);
        return ResponseEntity.ok("카테고리 등록이 완료되었습니다. ID: " + categoryId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCategory(@PathVariable Long id, @Valid @ModelAttribute EditCategoryRequest form, BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        categoryService.updateCategory(id, form);
        return ResponseEntity.ok("카테고리 수정이 완료되었습니다. ID: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
