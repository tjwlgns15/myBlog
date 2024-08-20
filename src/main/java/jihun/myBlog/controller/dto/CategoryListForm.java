package jihun.myBlog.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryListForm {
    private Long id;
    private String name;
    private String description;
}
