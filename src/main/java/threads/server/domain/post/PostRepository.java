package threads.server.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post save(Post member);
    Optional<Post> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"user", "comments", "comments.user"})
    Page<Post> findAll(Pageable pageable);
}
