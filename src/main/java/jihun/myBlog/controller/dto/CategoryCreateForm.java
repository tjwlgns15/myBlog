package jihun.myBlog.controller.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
public class CategoryCreateForm {

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String name;

    private String description;

}
