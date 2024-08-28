package jihun.myBlog.dto.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditCategoryRequest {

    private Long id;

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String name;

}
