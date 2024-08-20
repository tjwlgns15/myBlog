package jihun.myBlog.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jihun.myBlog.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryEditForm {

    private Long id;

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String name;

    private String description;
}
