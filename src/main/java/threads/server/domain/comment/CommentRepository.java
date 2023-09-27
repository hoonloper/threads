package threads.server.domain.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Integer countByPostId(Long postId);

    @Query("SELECT c FROM Comment c INNER JOIN FETCH c.user WHERE c.post.id = :postId")
    Page<Comment> findAllComments(Pageable pageable, Long postId);
}
