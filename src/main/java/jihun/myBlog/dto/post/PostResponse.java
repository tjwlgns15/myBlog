package jihun.myBlog.dto.post;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {

    private Long id;

    private String title;

    private String content;

    private String author;

    private String category;

    private LocalDateTime createdAt;

    private long viewCount;

}


