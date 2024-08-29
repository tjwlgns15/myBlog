package jihun.myBlog.service;

import jihun.myBlog.dto.comment.CommentResponse;
import jihun.myBlog.dto.comment.CreateCommentRequest;
import jihun.myBlog.dto.comment.EditCommentRequest;
import jihun.myBlog.entity.Comment;
import jihun.myBlog.entity.Member;
import jihun.myBlog.entity.Post;
import jihun.myBlog.global.exception.CustomException;
import jihun.myBlog.repository.CommentRepository;
import jihun.myBlog.repository.MemberRepository;
import jihun.myBlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static jihun.myBlog.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public List<CommentResponse> findCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPost_Id(postId);
        return comments.stream().map(
                comment -> CommentResponse.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .author(comment.getAuthor().getNickname())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(Long id, EditCommentRequest request) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(COMMENT_NOT_FOUND)
        );
        comment.update(request);
    }

    public Long savaComment(CreateCommentRequest request) {
        Post post = postRepository.findById(request.getPostId()).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        Member member = memberRepository.findById(request.getAuthorId()).orElseThrow(
                () -> new CustomException(USER_NOT_FOUND)
        );
        Comment comment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .author(member)
                .build();
        return commentRepository.save(comment).getId();
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(COMMENT_NOT_FOUND)
        );
    }
}
