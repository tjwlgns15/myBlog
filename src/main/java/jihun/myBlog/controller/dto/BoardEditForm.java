package jihun.myBlog.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jihun.myBlog.entity.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardEditForm {

    private Long id;

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String title;

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String content;

    private Category category;

}
