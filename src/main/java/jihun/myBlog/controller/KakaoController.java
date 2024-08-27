//package jihun.myBlog.controller;
//
//import jihun.myBlog.controller.dto.KakaoUserInfoResponseDto;
//import jihun.myBlog.service.KakaoService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.io.IOException;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("")
//@Slf4j
//public class KakaoController {
//
//    private final KakaoService kakaoService;
//
//    @Value("${kakao.client_id}")
//    private String client_id;
//
//    @Value("${kakao.redirect_uri}")
//    private String redirect_uri;
//
//    @Value("${kakao.logout_redirect_uri}")
//    private String logout_redirect_uri;
//
//
//    @GetMapping("/auth/login")
//    public String loginPage(Model model) {
//        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri;
//        model.addAttribute("location", location);
//
//        return "/kakao/login";
//    }
//
//    @GetMapping("/callback")
//    public String callback(@RequestParam("code") String code, Model model) {
//        String accessToken = kakaoService.getAccessTokenFromKakao(code);
//
//        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
//
//        String location = "https://kauth.kakao.com/oauth/logout?client_id="+client_id+"&logout_redirect_uri="+logout_redirect_uri;
//        model.addAttribute("location", location);
//
//        return "/kakao/main";
//    }
//
//    @GetMapping("/auth/logout")
//    public String logoutPage() {
//        log.info("logout");
//        return "/kakao/logout";
//    }
//
//}
