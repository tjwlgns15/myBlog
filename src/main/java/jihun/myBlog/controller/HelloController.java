package jihun.myBlog.controller;

import jihun.myBlog.dto.post.PostResponse;
import jihun.myBlog.dto.category.CategoryResponse;
import jihun.myBlog.service.PostService;
import jihun.myBlog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HelloController {

    private final CategoryService categoryService;
    private final PostService postService;

    @RequestMapping("/")
    public String home(@RequestParam(value = "categoryId", required = false) Long categoryId,
                       Model model,
                       @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        List<CategoryResponse> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);

        Page<PostResponse> postPage;
        String categoryName = "All Posts";

        if (categoryId != null) {
            postPage = postService.findPostsByCategory(categoryId, pageable);
            CategoryResponse category = categoryService.findCategory(categoryId);
            categoryName = category.getName();
        } else {
            postPage = postService.findAllPosts(pageable);
        }
        model.addAttribute("posts", postPage);
        model.addAttribute("categoryName", categoryName);


        return "test";
    }
}
