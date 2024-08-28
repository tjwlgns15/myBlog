package jihun.myBlog.repository;

import jihun.myBlog.entity.Member;
import jihun.myBlog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
