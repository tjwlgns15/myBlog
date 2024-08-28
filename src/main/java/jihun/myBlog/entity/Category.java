package jihun.myBlog.entity;

import jakarta.persistence.*;
import jihun.myBlog.dto.category.EditCategoryRequest;
import jihun.myBlog.global.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;


    public void updateCategory(EditCategoryRequest form) {
        this.name = form.getName();
    }
}
