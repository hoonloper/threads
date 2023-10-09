package threads.server.domain.like.service;

import threads.server.domain.like.dto.CreatingLikeDto;
import threads.server.domain.like.dto.DeletingLikeDto;

public interface LikeService {
    void save(CreatingLikeDto likeDto);
    void delete(DeletingLikeDto likeDto);
}
