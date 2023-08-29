package threads.server.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post save(Post member);
    Optional<Post> findById(Long id);

    @Query("select distinct p, c, u from Post p left join p.comments c left join p.user u order by p.createdAt desc")
    List<Post> findAllPost();
}
