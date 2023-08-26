package threads.server.domain.like.service;

import threads.server.domain.like.dto.CreatingLikeDTO;
import threads.server.domain.like.dto.DeletingLikeDTO;

public interface LikeService {
    void save(CreatingLikeDTO likeDTO);
    void delete(DeletingLikeDTO likeDTO);
}
