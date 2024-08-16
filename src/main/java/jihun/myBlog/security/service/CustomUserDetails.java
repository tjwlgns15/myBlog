package jihun.myBlog.security.service;

import jihun.myBlog.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

  private Long id;

  private String username;

  private String password;

  public static CustomUserDetails fromEntity(Member member) {
    return new CustomUserDetails(member.getId(), member.getUsername(), member.getPassword());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }
}
