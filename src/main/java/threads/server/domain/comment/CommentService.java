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
import threads.server.domain.like.repository.LikeCommentRepository;
import threads.server.domain.post.Post;
import threads.server.domain.reply.dto.ReplyDto;
import threads.server.domain.reply.repository.ReplyRepositorySupport;
import threads.server.domain.user.User;
import threads.server.domain.user.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

import static threads.server.domain.comment.dto.CommentDto.toCommentDto;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final LikeCommentRepository likeCommentRepository;
    private final CommentRepository commentRepository;
    private final CommentRepositorySupport commentRepositorySupport;
    private final ReplyRepositorySupport replyRepositorySupport;

    public CommentDto save(CreatingCommentDTO commentDto) {
        User user = new User(commentDto.userId());
        Post post = new Post(commentDto.postId());
        return toCommentDto(commentRepository.save(Comment.builder().user(user).post(post).content(commentDto.content()).build()));
    }

    public CommentDto update(UpdatingCommentDTO commentDto) {
        Comment comment = commentRepository.findById(commentDto.id()).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        authorizeUser(commentDto.userId(), comment.getUser().getId());
        comment.change(commentDto.content());
        commentRepository.save(comment);
        return toCommentDto(comment);
    }

    public void delete(DeletingCommentDTO commentDto) {
        Comment comment = commentRepository.findById(commentDto.id()).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        authorizeUser(commentDto.userId(), comment.getUser().getId());
        commentRepository.delete(comment);
    }


    private void authorizeUser(Long requestUserId, Long userIdFromComment) {
        if (!requestUserId.equals(userIdFromComment)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
    }

    @Transactional
    public ReadCommentDto findAllByPostId(Pageable pageable, Long postId, Long userId) {
        PageImpl<Comment> commentPage = commentRepositorySupport.findCommentPage(pageable, postId);
        List<CommentDto> commentDtoList =  commentRepositorySupport.findAllComments(pageable, postId, userId)
                .stream()
                .map(comment -> {
                    // TODO: 좋아요 여부 가져오는 쿼리 수정해야 함
                    // TODO: 순회로 댓글의 답글 가져오는 쿼리 개선해야 함
                    comment.setLiked(likeCommentRepository.findByUserIdAndCommentId(userId, comment.getId()).isPresent());
                    ReplyDto replyDto = replyRepositorySupport.findOneByCommentId(comment.getId(), userId);
                    if(replyDto != null) {
                        comment.getReplies().add(replyDto);
                    }
                    comment.setUser(UserDto.toDto(comment.getUserEntity()));
                    return comment;
                })
                .toList();
        return new ReadCommentDto(commentPage.getTotalPages(), commentPage.getTotalElements(), commentDtoList);
    }
}
