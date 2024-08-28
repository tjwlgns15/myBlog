package jihun.myBlog.dto.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {
    private Long id;

    private String content;

    private String author;
}
