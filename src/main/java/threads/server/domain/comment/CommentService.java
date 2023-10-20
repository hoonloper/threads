package threads.server.domain.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import threads.server.application.exception.ForbiddenException;
import threads.server.application.exception.NotFoundException;
import threads.server.domain.comment.dto.*;
import threads.server.domain.comment.repository.CommentRepository;
import threads.server.domain.comment.repository.CommentRepositorySupport;
import threads.server.domain.reply.dto.ReplyDto;
import threads.server.domain.reply.repository.ReplyRepositorySupport;

import java.util.List;

import static threads.server.domain.comment.dto.CommentDto.toCommentDto;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentRepositorySupport commentRepositorySupport;
    private final ReplyRepositorySupport replyRepositorySupport;

    public CommentDto save(final CreatingCommentDto commentDto) {
        return toCommentDto(commentRepository.save(Comment.toComment(commentDto)));
    }

    @Transactional
    public CommentDto update(final UpdatingCommentDto commentDto) {
        Comment comment = commentRepository.findById(commentDto.getId()).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        if(!comment.checkIfAuthor(commentDto.getUserId())) {
            throw new ForbiddenException("권한이 없습니다.");
        };
        comment.change(commentDto.getContent());
        commentRepository.save(comment);
        return toCommentDto(comment);
    }

    @Transactional
    public void delete(final Long commentId, final Long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        if(!comment.checkIfAuthor(userId)) {
            throw new ForbiddenException("권한이 없습니다.");
        };
        commentRepository.delete(comment);
    }

    @Transactional
    public ReadCommentDto findAllByPostId(final Pageable pageable, final Long postId, final Long userId) {
        PageImpl<Comment> commentPage = commentRepositorySupport.findCommentPageByPostId(pageable, postId);
        // TODO: 순회로 댓글의 답글 가져오는 쿼리 개선해야 함
        List<CommentDto> commentDtoList =  commentRepositorySupport.findAllComments(pageable, postId, userId)
                .stream()
                .peek(comment -> addFoundLastReplyFromComment(comment, userId))
                .toList();
        return new ReadCommentDto(commentPage.getTotalPages(), commentPage.getTotalElements(), commentDtoList);
    }

    @Transactional
    public ReadCommentDto findAllByUserId(final Pageable pageable, final Long userId) {
        PageImpl<Comment> commentPage = commentRepositorySupport.findCommentPageByUserId(pageable, userId);
        // TODO: 순회로 댓글의 답글 가져오는 쿼리 개선해야 함
        List<CommentDto> commentDtoList =  commentRepositorySupport.findAllCommentsByUserId(pageable, userId)
                .stream()
                .peek(comment -> addFoundLastReplyFromComment(comment, userId))
                .toList();
        return new ReadCommentDto(commentPage.getTotalPages(), commentPage.getTotalElements(), commentDtoList);
    }

    private void addFoundLastReplyFromComment(final CommentDto comment, final Long userId) {
        replyRepositorySupport.findOneByCommentId(comment.getId(), userId).ifPresent(comment::addReply);
    }
}
