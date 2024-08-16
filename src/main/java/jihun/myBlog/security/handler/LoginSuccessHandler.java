package jihun.myBlog.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jihun.myBlog.security.JwtProvider;
import jihun.myBlog.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static jihun.myBlog.security.AuthConstraint.TOKEN_HEADER_NAME;
import static jihun.myBlog.security.AuthConstraint.TOKEN_PREFIX;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtProvider jwtProvider;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

    Long memberId = principal.getId();

    String token = jwtProvider.generateToken(memberId);

    response.setHeader(TOKEN_HEADER_NAME, TOKEN_PREFIX+ token);
    response.setContentType(APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(UTF_8.name());
    response.setStatus(HttpStatus.OK.value());
    response.sendRedirect("/main");
  }
}
