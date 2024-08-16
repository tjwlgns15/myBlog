package jihun.myBlog.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

  private final ObjectMapper objectMapper;

  public LoginFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
      super("/auth/login", authenticationManager);
      this.objectMapper = objectMapper;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

    LoginRequest loginRequest;

    // 요청 바디에서 json 확인, 실패 시 폼 데이터 확인
    try {
      String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
      loginRequest = objectMapper.readValue(body, LoginRequest.class);
    } catch (IOException e) {
      String username = request.getParameter("username");
      String password = request.getParameter("password");
      loginRequest = new LoginRequest(username, password);
    }

    UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(
            loginRequest.username(),
            loginRequest.password()
    );

    return this.getAuthenticationManager().authenticate(authRequest);
  }

  public record LoginRequest(String username, String password) {
  }


}
