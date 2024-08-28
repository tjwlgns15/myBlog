package jihun.myBlog.dto.category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

    private Long id;

    private String name;
}
