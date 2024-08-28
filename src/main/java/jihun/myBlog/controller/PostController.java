package jihun.myBlog.controller;

import jakarta.validation.Valid;
import jihun.myBlog.dto.category.CategoryResponse;
import jihun.myBlog.dto.comment.CommentResponse;
import jihun.myBlog.dto.post.CreatePostRequest;
import jihun.myBlog.dto.post.EditPostRequest;
import jihun.myBlog.dto.post.PostResponse;
import jihun.myBlog.entity.Post;
import jihun.myBlog.service.CategoryService;
import jihun.myBlog.service.CommentService;
import jihun.myBlog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final CategoryService categoryService;

//
//    @GetMapping("")
//    public String listForm(Model model) {
//        List<PostResponse> posts = postService.findPosts();
//
//        model.addAttribute("posts", posts);
//
//        List<CategoryResponse> categories = categoryService.findCategories();
//
//        model.addAttribute("categories", categories);
//        return "/post/";
//    }

    // 작성 폼
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("createPostRequest", new CreatePostRequest());

        List<CategoryResponse> categoryResponse = categoryService.findCategories();
        model.addAttribute("categoryResponse", categoryResponse);
        return "/post/postCreate";
    }

    // 작성
    @PostMapping("/new")
    public String createPost(@Valid @ModelAttribute CreatePostRequest request, BindingResult result) {
        if (result.hasErrors()) {
            log.info("[Error] fail to save post: {}",result.getAllErrors());
            return "/post/postCreate";
        }
        Long postId = postService.savePost(request);
        return "redirect:/post/" + postId;
    }

    // 조회 폼
    @GetMapping("/{id}")
    public String detailForm(@PathVariable Long id, Model model) {
        Post post = postService.findPost(id);
        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor().getNickname())
                .category(post.getCategory().getName())
                .viewCount(post.getViewCount())
                .createdAt(post.getCreatedAt())
                .build();
        model.addAttribute("postResponse", postResponse);

        List<CommentResponse> commentResponse = commentService.findCommentsByPostId(id);
        model.addAttribute("commentResponses", commentResponse);

        return "/post/postDetail";
    }

    // 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Post post = postService.findPost(id);
        EditPostRequest editPostRequest = EditPostRequest.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
//                .authorId(post.getAuthor().getId())
                .categoryId(post.getCategory().getId())
                .build();
        model.addAttribute("editPostRequest", editPostRequest);
        return "/post/postEdit";
    }

    // 수정
    @PutMapping("/{id}")
    public String editPost(@PathVariable Long id, @ModelAttribute @Valid EditPostRequest request, BindingResult result) {
        log.info("id: {}, request: {}", id, request);


        if (result.hasErrors()) {
            log.info("[Error] fail to edit post: {}",result.getAllErrors());
            return "/post/postEdit";
        }
        postService.updatePost(id, request);
        return "redirect:/post/" + id;
    }

    // 삭제
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/";
    }

}
