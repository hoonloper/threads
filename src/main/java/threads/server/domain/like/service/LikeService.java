package threads.server.domain.like.service;

import threads.server.domain.like.LikeDTO;

public interface LikeService {
    void save(LikeDTO likeDTO);
    void delete(LikeDTO likeDTO);
}
