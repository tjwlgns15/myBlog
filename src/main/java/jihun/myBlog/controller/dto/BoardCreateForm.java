package jihun.myBlog.controller.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jihun.myBlog.entity.Category;
import jihun.myBlog.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardCreateForm {

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String title;

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String content;

    private Category category;
}
