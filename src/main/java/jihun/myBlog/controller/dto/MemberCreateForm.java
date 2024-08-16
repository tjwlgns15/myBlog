package jihun.myBlog.controller.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jihun.myBlog.entity.Gender;
import jihun.myBlog.entity.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
public class MemberCreateForm {

    @NotEmpty(message = "필수 입력 사항입니다.")
    @Email(message = "email 형식으로 입력해 주세요.")
    private String username;

    @NotEmpty(message = "필수 입력 사항입니다.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).{8,}$",
            message = "비밀번호는 특수문자, 숫자, 대문자 또는 소문자를 포함한 8자리 이상으로 만들어주세요."
    )
    private String password;

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String nickName;

    @NotEmpty(message = "필수 입력 사항입니다.")
    private String phoneNumber;

//    @NotEmpty(message = "필수 입력 사항입니다.")
    private Gender gender;

//    @NotEmpty(message = "필수 입력 사항입니다.")
    private LocalDate birthday;

    @Override
    public String toString() {
        return "MemberCreateForm{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                '}';
    }
}
