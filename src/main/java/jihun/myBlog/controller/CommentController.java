package jihun.myBlog.controller;


import jakarta.validation.Valid;
import jihun.myBlog.dto.comment.CreateCommentRequest;
import jihun.myBlog.dto.comment.EditCommentRequest;
import jihun.myBlog.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Slf4j
public class CommentController {
    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/new")
    public String createComment(@ModelAttribute @Valid CreateCommentRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("[Error] fail to create comment: {}", bindingResult.getAllErrors());
            return "redirect:/post/" + request.getPostId();
        }
        commentService.savaComment(request);
        return "redirect:/post/" + request.getPostId();
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public String editComment(@PathVariable Long id, @ModelAttribute @Valid EditCommentRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("[Error] fail to edit comment: {}", bindingResult.getAllErrors());
            return "redirect:/post/" + request.getPostId();
        }
        commentService.updateComment(id, request);
        return "redirect:/post/" + request.getPostId();
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id) {
        Long postId = commentService.findCommentById(id).getPost().getId();
        commentService.deleteComment(id);
        return "redirect:/post/" + postId;

    }
}
