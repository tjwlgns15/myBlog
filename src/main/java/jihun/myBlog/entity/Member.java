package jihun.myBlog.entity;

import jakarta.persistence.*;
import jihun.myBlog.global.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @Column(length = 100, nullable = false, unique = true)
    private String nickName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 500)
    private String introduction;

    @Column(length = 255)
    private String picture = "default-profile-picture.png";  // todo: 기본 이미지 설정

}
