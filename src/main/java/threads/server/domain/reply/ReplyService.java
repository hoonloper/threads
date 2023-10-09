package threads.server.domain.reply;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.application.exception.UnauthorizedException;
import threads.server.domain.comment.Comment;
import threads.server.domain.like.repository.LikeCommentRepository;
import threads.server.domain.like.repository.LikeReplyRepository;
import threads.server.domain.reply.dto.*;
import threads.server.domain.reply.repository.ReplyRepository;
import threads.server.domain.reply.repository.ReplyRepositorySupport;
import threads.server.domain.user.User;
import threads.server.domain.user.dto.UserDto;

import java.util.List;

import static threads.server.domain.reply.dto.ReplyDto.toReplyDto;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    private final ReplyRepositorySupport replyRepositorySupport;
    private final LikeReplyRepository likeReplyRepository;

    public ReplyDto save(CreatingReplyDto replyDto) {
        User user = new User(replyDto.getUserId());
        Comment comment = Comment.builder().id(replyDto.getCommentId()).build();
        return toReplyDto(replyRepository.save(new Reply(null, user, comment, replyDto.getContent())));
    }

    public ReplyDto update(UpdatingReplyDto replyDto, Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        authorizeUser(replyDto.getUserId(), reply.getUser().getId());
        reply.change(replyDto.getContent());
        replyRepository.save(reply);
        return toReplyDto(reply);
    }

    public void delete(Long id, Long userId) {
        Reply reply = replyRepository.findById(id).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        authorizeUser(userId, reply.getUser().getId());
        replyRepository.delete(reply);
    }


    private void authorizeUser(Long requestUserId, Long userIdFromComment) {
        if (!requestUserId.equals(userIdFromComment)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
    }

    public ReadReplyDto findAllByCommentId(Pageable pageable, Long commentId, Long userId) {
        PageImpl<Reply> replyPage = replyRepositorySupport.findReplyPage(pageable, commentId);
        List<ReplyDto> replyList = replyRepositorySupport.findAllReplies(pageable, commentId, userId)
                .stream()
                .map(reply -> {
                    reply.setLikeCount(likeReplyRepository.countByReplyId(reply.getId()));
                    reply.setLiked(likeReplyRepository.findByUserIdAndReplyId(userId, reply.getId()).isPresent());
                    reply.setUser(UserDto.toDto(reply.getUserEntity()));
                    return reply;
                }
        ).toList();
        return new ReadReplyDto(replyPage.getTotalPages(), replyPage.getTotalElements(), replyList);
    }
}
