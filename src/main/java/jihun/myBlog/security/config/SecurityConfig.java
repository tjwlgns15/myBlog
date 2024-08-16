package jihun.myBlog.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jihun.myBlog.repository.MemberRepository;
import jihun.myBlog.security.JwtProvider;
import jihun.myBlog.security.filter.ExceptionHandlingFilter;
import jihun.myBlog.security.filter.JwtAuthenticationFilter;
import jihun.myBlog.security.filter.LoginFilter;
import jihun.myBlog.security.handler.LoginFailureHandler;
import jihun.myBlog.security.handler.LoginSuccessHandler;
import jihun.myBlog.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;

    private final MemberRepository memberRepository;

    private final LoginSuccessHandler loginSuccessHandler;

    private final LoginFailureHandler loginFailureHandler;

    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        (configurer) -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/user/**", "/boards/**", "game/**").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers("/main/**", "/auth/**").permitAll()
//                        .anyRequest().authenticated()
//                )

//            .authorizeHttpRequests((requests) -> requests
//                    .requestMatchers("/auth/**").permitAll()  // 공개 경로 허용
//                    .anyRequest().authenticated()  // 나머지 모든 요청은 인증 필요
//            )
                .addFilterBefore(exceptionHandlingFilter(), LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }


    @Bean
    public ExceptionHandlingFilter exceptionHandlingFilter() {
        return new ExceptionHandlingFilter(objectMapper);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtProvider, memberRepository);
    }

    @Bean
    public LoginFilter loginFilter() {
        LoginFilter loginFilter = new LoginFilter(authenticationManager(), objectMapper);
        loginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        loginFilter.setAuthenticationFailureHandler(loginFailureHandler);

        return loginFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(provider);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(memberRepository);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
