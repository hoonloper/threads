package threads.server.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post save(Post member);
    Optional<Post> findById(Long id);

//    SELECT qs FROM QuizState qs join fetch qs.quiz join fetch qs.quizStateType WHERE qs.user = ?1 and qs.quizStateType.desc = 'NOT_SELECTED'
//    JOIN FETCH p.user u JOIN FETCH p.comments c
    @Query("SELECT p FROM Post p INNER JOIN FETCH p.user")
    Page<Post> findAllPosts(Pageable pageable);
}
