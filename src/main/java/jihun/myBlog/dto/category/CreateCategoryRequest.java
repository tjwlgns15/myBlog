package jihun.myBlog.dto.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreateCategoryRequest {

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String name;
}
