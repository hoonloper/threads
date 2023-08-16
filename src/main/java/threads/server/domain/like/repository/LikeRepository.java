package threads.server.domain.like.repository;

public interface LikeRepository<T> {
    T save(T entity);
}
