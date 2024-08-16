package jihun.myBlog.controller.dto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jihun.myBlog.entity.Category;
import jihun.myBlog.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardDetailForm {

    private Long id;

    private String title;

    private String content;

    private Member author;

    private Category category;

    private long viewCount;

    private LocalDateTime createdAt;

}
