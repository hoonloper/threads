package threads.server.domain.like.service;

import threads.server.domain.like.dto.CreatingLikeDTO;
import threads.server.domain.like.dto.RemovingLikeDTO;

public interface LikeService {
    void save(CreatingLikeDTO likeDTO);
    void delete(RemovingLikeDTO likeDTO);
}
