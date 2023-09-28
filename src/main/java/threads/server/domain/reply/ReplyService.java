package threads.server.domain.reply;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.application.exception.UnauthorizedException;
import threads.server.domain.comment.Comment;
import threads.server.domain.like.repository.LikeCommentRepository;
import threads.server.domain.reply.dto.*;
import threads.server.domain.reply.repository.ReplyRepository;
import threads.server.domain.user.User;

import java.util.List;

import static threads.server.domain.reply.dto.ReplyDto.toReplyDto;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    private final LikeCommentRepository likeCommentRepository;

    public ReplyDto save(CreatingReplyDto replyDto) {
        User user = new User(replyDto.getUserId());
        Comment comment = Comment.builder().id(replyDto.getCommentId()).build();
        return toReplyDto(replyRepository.save(new Reply(null, user, comment, replyDto.getContent())));
    }

    public ReplyDto update(UpdatingReplyDto replyDto) {
        Reply reply = replyRepository.findById(replyDto.getId()).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        authorizeUser(replyDto.getUserId(), reply.getUser().getId());
        reply.change(replyDto.getContent());
        replyRepository.save(reply);
        return toReplyDto(reply);
    }

    public void delete(DeletingReplyDto replyDto) {
        Reply reply = replyRepository.findById(replyDto.getId()).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        authorizeUser(replyDto.getUserId(), reply.getUser().getId());
        replyRepository.delete(reply);
    }


    private void authorizeUser(Long requestUserId, Long userIdFromComment) {
        if (!requestUserId.equals(userIdFromComment)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
    }

    public ReadReplyDto findAllByCommentId(Pageable pageable, Long commentId, Long userId) {
        Page<Reply> replies = replyRepository.findAllReplies(pageable, commentId);
        List<ReplyDto> replyList = replies.stream().map(reply -> {
            ReplyDto replyDto = toReplyDto(reply);
            // TODO: replyDto.setReplyCount(replyRepository.countByPostId(reply.getId()));
            replyDto.setLikeCount(likeCommentRepository.countByCommentId(reply.getId()));
            replyDto.setLiked(likeCommentRepository.findByUserIdAndCommentId(userId, reply.getId()).isPresent());
            return replyDto;
        }).toList();
        return new ReadReplyDto(replies.getTotalPages(), replies.getTotalElements(), replyList);
    }
}
