package jihun.myBlog.controller;

import jihun.myBlog.dto.post.PostResponse;
import jihun.myBlog.dto.category.CategoryResponse;
import jihun.myBlog.service.PostService;
import jihun.myBlog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HelloController {

    private final CategoryService categoryService;
    private final PostService postService;

    @RequestMapping("/")
    public String home(Model model) {
        List<CategoryResponse> categories = categoryService.findCategories();
        model.addAttribute("categories", categories);

        List<PostResponse> posts = postService.findPosts();
        model.addAttribute("posts", posts);
        return "index";
    }
}
