package jihun.myBlog.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jihun.myBlog.entity.Category;
import jihun.myBlog.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CategoryDetailForm {

    private Long id;

    private String name;

    private String description;

}
