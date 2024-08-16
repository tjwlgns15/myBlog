package jihun.myBlog.controller.dto;

import jihun.myBlog.entity.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardListForm {

    private String title;

    private String author;

    private Category category;

    private LocalDateTime createdAt;

    private long viewCount;
}
