package jihun.myBlog.repository;

import jihun.myBlog.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickName);

    Optional<Member> findByLoginId(String loginId);
}
