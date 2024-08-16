package jihun.myBlog.controller;

import jakarta.validation.Valid;
import jihun.myBlog.controller.dto.MemberCreateForm;
import jihun.myBlog.entity.Member;
import jihun.myBlog.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("memberForm", new MemberCreateForm());
        return "member/register";
    }
    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("register")
    public String register(@Valid @ModelAttribute MemberCreateForm form, BindingResult result) {

        // DTO 또는 폼 객체가 사용된 요청에 @Valid를 사용해 유효성 검증, 검증 결과를 BindingResult에 저장 후 에러 발생 시 반환
        if (result.hasErrors()) {
            log.error("[Error] fail to save member: {}", result.getAllErrors().toString());
            return "member/register";
        }

        memberService.join(form);
        return "member/login";
    }

}
