package jihun.myBlog.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jihun.myBlog.entity.Member;
import jihun.myBlog.exception.CustomException;
import jihun.myBlog.repository.MemberRepository;
import jihun.myBlog.security.JwtProvider;
import jihun.myBlog.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static jihun.myBlog.exception.ErrorCode.*;
import static jihun.myBlog.security.AuthConstraint.TOKEN_HEADER_NAME;
import static jihun.myBlog.security.AuthConstraint.TOKEN_PREFIX;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 토큰 유무 체크
        String bearerToken = request.getHeader(TOKEN_HEADER_NAME);
        if (!StringUtils.hasText(bearerToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!bearerToken.startsWith(TOKEN_PREFIX)) {
            throw new CustomException(INVALID_TOKEN);
        }

        String token = bearerToken.substring(TOKEN_PREFIX.length());

        // 토큰 만료 체크 추가
        if (jwtProvider.isTokenExpired(token)) {
            throw new CustomException(TOKEN_EXPIRED);  // 적절한 에러 코드 사용
        }

        Long memberId = Long.parseLong(jwtProvider.parserToken(token));

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(MEMBER_NOT_FOUND)
        );

        CustomUserDetails principal = CustomUserDetails.fromEntity(member);

        // 토큰 유효성 추가 검증
        if (!jwtProvider.validateToken(token, principal.getUsername())) {
            throw new CustomException(INVALID_TOKEN);
        }

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(
                new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities())
        );

        filterChain.doFilter(request, response);
    }
}