package threads.server.domain.reply;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import threads.server.application.exception.NotFoundException;
import threads.server.application.exception.UnauthorizedException;
import threads.server.domain.comment.Comment;
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

    public ReplyDto save(final CreatingReplyDto replyDto) {
        User user = new User(replyDto.getUserId());
        Comment comment = new Comment(replyDto.getCommentId());
        return toReplyDto(replyRepository.save(new Reply(null, user, comment, replyDto.getContent())));
    }

    @Transactional
    public ReplyDto update(final UpdatingReplyDto replyDto, Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        if (!reply.checkIfAuthor(replyDto.getUserId())) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        reply.change(replyDto.getContent());
        replyRepository.save(reply);
        return toReplyDto(reply);
    }

    @Transactional
    public void delete(final Long id, final Long userId) {
        Reply reply = replyRepository.findById(id).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        if (!reply.checkIfAuthor(userId)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        replyRepository.delete(reply);
    }

    public ReadReplyDto findAllByCommentId(final Pageable pageable, final Long commentId, final Long userId) {
        PageImpl<Reply> replyPage = replyRepositorySupport.findReplyPage(pageable, commentId);
        List<ReplyDto> replyList = replyRepositorySupport.findAllReplies(pageable, commentId, userId)
                .stream()
                .peek(reply -> reply.setUser(UserDto.toDto(reply.getUserEntity())))
                .toList();
        return new ReadReplyDto(replyPage.getTotalPages(), replyPage.getTotalElements(), replyList);
    }
}
