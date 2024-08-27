package jihun.myBlog.controller;

import jakarta.validation.Valid;
import jihun.myBlog.auth.JwtTokenUtil;
import jihun.myBlog.controller.dto.JoinRequest;
import jihun.myBlog.controller.dto.LoginRequest;
import jihun.myBlog.entity.Member;
import jihun.myBlog.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @Value("${jwt.key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expireTimeMs;

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("joinRequest", new JoinRequest());
        return "/auth/join";
    }

    @PostMapping("/join")
    public String join(@RequestBody @Valid JoinRequest joinRequest, BindingResult result) {
        log.info("join request: {}", joinRequest);

        if (result.hasErrors()) {
//            return "/auth/join";
        }

        /**
         * todo: 중복, 불일치 메세지 팝업, 모달 출력
         */
        // loginId 중복 체크
        if(memberService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
            return "로그인 아이디가 중복됩니다.";
        }
        // 닉네임 중복 체크
        if(memberService.checkNicknameDuplicate(joinRequest.getNickname())) {
            return "닉네임이 중복됩니다.";
        }
        // password와 passwordCheck가 같은지 체크
        if(!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            return "바밀번호가 일치하지 않습니다.";
        }
        memberService.join2(joinRequest);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "/auth/login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        log.info("login request: {}", loginRequest);
        Member user = memberService.login(loginRequest);
        /**
         * todo: 토큰 받아 로컬 스토리지 or 쿠키 저장
         */
        // Jwt 발급
        String token = JwtTokenUtil.createToken(user.getLoginId(), secretKey, expireTimeMs);
        log.info("token: {}", token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity.ok().headers(headers).body("로그인 성공");
    }

    @GetMapping("/info")
    public String userInfo(Authentication auth) {
        log.info("auth: {}", auth);
        Member loginUser = memberService.getLoginUserByLoginId(auth.getName());

        return String.format("loginId : %s\nnickname : %s\nrole : %s",
                loginUser.getLoginId(), loginUser.getNickname(), loginUser.getRole().name());
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "관리자 페이지 접근 성공";
    }
}
