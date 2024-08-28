package jihun.myBlog.service;

import jihun.myBlog.dto.post.EditPostRequest;
import jihun.myBlog.dto.post.CreatePostRequest;
import jihun.myBlog.dto.post.PostResponse;
import jihun.myBlog.entity.Member;
import jihun.myBlog.entity.Post;
import jihun.myBlog.entity.Category;
import jihun.myBlog.global.exception.CustomException;
import jihun.myBlog.repository.CategoryRepository;
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
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    public Long savePost(CreatePostRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                () -> new CustomException(CATEGORY_NOT_FOUND)
        );
        Member member = memberRepository.findById(request.getAuthorId()).orElseThrow(
                () -> new CustomException(USER_NOT_FOUND)
        );

        // todo: 현재 로그인 정보
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(member)
                .category(category)
                .viewCount(0L)
                .build();
        return postRepository.save(post).getId();
    }

    public List<PostResponse> findPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(
                post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor().getNickname())
                        .category(post.getCategory().getName())
                        .createdAt(post.getCreatedAt())
                        .viewCount(post.getViewCount())
                        .build())
                .collect(Collectors.toList());
    }

    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
    }

    @Transactional
    public void updatePost(Long id, EditPostRequest form) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        Category category = categoryRepository.findById(form.getCategoryId()).orElseThrow(
                () -> new CustomException(CATEGORY_NOT_FOUND)
        );
        post.updatePost(form, category);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
