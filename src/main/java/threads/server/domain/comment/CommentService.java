package threads.server.domain.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import threads.server.application.exception.NotFoundException;
import threads.server.application.exception.UnauthorizedException;
import threads.server.domain.comment.dto.*;
import threads.server.domain.comment.repository.CommentRepository;
import threads.server.domain.comment.repository.CommentRepositorySupport;
import threads.server.domain.post.Post;
import threads.server.domain.reply.dto.ReplyDto;
import threads.server.domain.reply.repository.ReplyRepositorySupport;
import threads.server.domain.user.User;
import threads.server.domain.user.dto.UserDto;

import java.util.List;

import static threads.server.domain.comment.dto.CommentDto.toCommentDto;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentRepositorySupport commentRepositorySupport;
    private final ReplyRepositorySupport replyRepositorySupport;

    public CommentDto save(CreatingCommentDto commentDto) {
        User user = new User(commentDto.getUserId());
        Post post = new Post(commentDto.getPostId());
        return toCommentDto(commentRepository.save(Comment.builder().user(user).post(post).content(commentDto.getContent()).build()));
    }

    @Transactional
    public CommentDto update(UpdatingCommentDto commentDto) {
        Comment comment = commentRepository.findById(commentDto.getId()).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        authorizeUser(commentDto.getUserId(), comment.getUser().getId());
        comment.change(commentDto.getContent());
        commentRepository.save(comment);
        return toCommentDto(comment);
    }

    @Transactional
    public void delete(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        authorizeUser(userId, comment.getUser().getId());
        commentRepository.delete(comment);
    }


    private void authorizeUser(Long requestUserId, Long userIdFromComment) {
        if (!requestUserId.equals(userIdFromComment)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
    }

    @Transactional
    public ReadCommentDto findAllByPostId(Pageable pageable, Long postId, Long userId) {
        PageImpl<Comment> commentPage = commentRepositorySupport.findCommentPageByPostId(pageable, postId);
        List<CommentDto> commentDtoList =  commentRepositorySupport.findAllComments(pageable, postId, userId)
                .stream()
                .peek(comment -> {
                    // TODO: 순회로 댓글의 답글 가져오는 쿼리 개선해야 함
                    ReplyDto replyDto = replyRepositorySupport.findOneByCommentId(comment.getId(), userId);
                    if(replyDto != null) {
                        comment.getReplies().add(replyDto);
                    }
                })
                .toList();
        return new ReadCommentDto(commentPage.getTotalPages(), commentPage.getTotalElements(), commentDtoList);
    }

    @Transactional
    public ReadCommentDto findAllByUserId(Pageable pageable, Long userId) {
        PageImpl<Comment> commentPage = commentRepositorySupport.findCommentPageByUserId(pageable, userId);
        List<CommentDto> commentDtoList =  commentRepositorySupport.findAllCommentsByUserId(pageable, userId)
                .stream()
                .peek(comment -> {
                    // TODO: 순회로 댓글의 답글 가져오는 쿼리 개선해야 함
                    ReplyDto replyDto = replyRepositorySupport.findOneByCommentId(comment.getId(), userId);
                    if(replyDto != null) {
                        comment.getReplies().add(replyDto);
                    }
                })
                .toList();
        return new ReadCommentDto(commentPage.getTotalPages(), commentPage.getTotalElements(), commentDtoList);
    }
}
