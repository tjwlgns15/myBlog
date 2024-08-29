package jihun.myBlog.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@ToString
public class PostResponse {

    private Long id;

    private String title;

    private String content;

    private String author;

    private String category;

    private LocalDateTime createdAt;

    private long viewCount;

    public String getFormattedCreatedAt() {
        return this.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }


}


