package jihun.myBlog.dto.post;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class EditPostRequest {

    private Long id;

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String title;

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String content;

    private Long categoryId;
}
