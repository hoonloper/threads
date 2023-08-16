package threads.server.domain.like.service;

import threads.server.domain.like.LikeDTO;

public abstract class LikeService {
    abstract public LikeDTO save(LikeDTO likeDTO);
    abstract void delete(LikeDTO likeDTO);
}
