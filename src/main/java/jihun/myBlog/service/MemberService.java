package jihun.myBlog.service;

import jihun.myBlog.controller.dto.MemberCreateForm;
import jihun.myBlog.entity.Member;
import jihun.myBlog.exception.CustomException;
import jihun.myBlog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static jihun.myBlog.exception.ErrorCode.ALREADY_EXISTS_NICKNAME;
import static jihun.myBlog.exception.ErrorCode.ALREADY_EXISTS_USERNAME;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void join(MemberCreateForm form) {
        // 중복 회원 검증
        if (memberRepository.existsByUsername(form.getUsername())) {
            throw new CustomException(ALREADY_EXISTS_USERNAME);
        }
        if (memberRepository.existsByNickName(form.getNickName())) {
            throw new CustomException(ALREADY_EXISTS_NICKNAME);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(form.getPassword());

        Member member = Member.builder()
                .username(form.getUsername())
                .password(encodedPassword)
                .nickName(form.getNickName())
                .phoneNumber(form.getPhoneNumber())
                .gender(form.getGender())
                .birthday(form.getBirthday())
                .build();

        memberRepository.save(member);
    }
}
