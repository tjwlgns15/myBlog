package jihun.myBlog.security.service;

import jihun.myBlog.entity.Member;
import jihun.myBlog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Member member = memberRepository.findByUsername(username).orElseThrow(
        () -> new RuntimeException("아이디 혹은 비밀번호가 올바르지 않습니다."));

    return CustomUserDetails.fromEntity(member);
  }
}
