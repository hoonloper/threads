package threads.server.domain.like.service;

import org.springframework.stereotype.Service;
import threads.server.domain.like.LikeType;
import threads.server.domain.like.dto.CreatingLikeDto;
import threads.server.domain.like.dto.DeletingLikeDto;

import java.util.HashMap;
import java.util.Map;

@Service
public class LikeServiceImpl implements LikeService {
    private final Map<LikeType, LikeService> likeServiceMap = new HashMap<>();

    public LikeServiceImpl(LikePostServiceImpl likePostServiceImpl, LikeCommentServiceImpl likeCommentServiceImpl, LikeReplyServiceImpl likeReplyServiceImpl) {
        likeServiceMap.put(LikeType.POST, likePostServiceImpl);
        likeServiceMap.put(LikeType.COMMENT, likeCommentServiceImpl);
        likeServiceMap.put(LikeType.REPLY, likeReplyServiceImpl);
    }

    public void save(CreatingLikeDto likeDto) {
        likeServiceMap.get(likeDto.getType()).save(likeDto);
    }

    public void delete(DeletingLikeDto likeDto) {
        likeServiceMap.get(likeDto.getType()).delete(likeDto);
    }
}
