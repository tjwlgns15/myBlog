package jihun.myBlog.auth.config;

import jihun.myBlog.auth.filter.JwtTokenFilter;
import jihun.myBlog.entity.Role;
import jihun.myBlog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${jwt.key}")
    private String secretKey;

    private final MemberService memberService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/api/auth/join").permitAll();
//                    auth.requestMatchers("/api/auth/login").permitAll();
////                    auth.requestMatchers("/api/auth/info").authenticated();
//                    auth.requestMatchers("/api/auth/admin/**").hasAuthority(Role.ADMIN.name());
//                })
                .addFilterBefore(new JwtTokenFilter(memberService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
