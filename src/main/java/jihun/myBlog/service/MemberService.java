package jihun.myBlog.service;

import jihun.myBlog.dto.auth.JoinRequest;
import jihun.myBlog.dto.auth.LoginRequest;
import jihun.myBlog.entity.Member;
import jihun.myBlog.global.exception.CustomException;
import jihun.myBlog.global.exception.ErrorCode;
import jihun.myBlog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j

public class MemberService {

    private final MemberRepository memberRepository
            ;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * 암호화 x
     */
    public void join(JoinRequest req) {
        memberRepository.save(req.toEntity());
    }

    /**
     * 회원가입2
     * 암호화 o
     */
    public void join2(JoinRequest req) {
        memberRepository.save(req.toEntity(passwordEncoder.encode(req.getPassword())));
    }

    /**
     *  로그인
     *  loginId 유무 확인, password 일치 확인
     *  불일치 유저 loginId 로그 출력
     */
    public Member login(LoginRequest req) {
        Member user = memberRepository.findByLoginId(req.getLoginId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            log.info("[Password Mismatch] {}", req.getLoginId());
            return null;
        }
        return user;
    }

    /**
     * loginId 중복 체크
     */
    public boolean checkLoginIdDuplicate(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    /**
     * nickname 중복 체크
     */
    public boolean checkNicknameDuplicate(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    /**
     * loginId로 유저 정보 찾기
     */
    public Member getLoginUserByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }

    /**
     * 인증시 사용
     * userId(Long)를 입력받아 User 리턴
     * 비로그인(null) 또는 올바른 유저인지 확인
     */
    public Member getLoginUserById(Long userId) {

        if (userId == null) return null;
        return memberRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }
}
