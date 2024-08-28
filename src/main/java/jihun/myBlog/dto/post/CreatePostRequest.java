package jihun.myBlog.dto.post;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePostRequest {

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String title;

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String content;

    private Long authorId;

    private Long categoryId;
}
