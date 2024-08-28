package jihun.myBlog.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class EditCommentRequest {

    Long id;

    @NotEmpty(message = "댓글이 입력되지 않았습니다.")
    private String content;

    private Long postId;

    private Long authorId;
}
