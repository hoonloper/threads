package threads.server.domain.activity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import threads.server.domain.activity.ActivityStatus;
import threads.server.domain.comment.dto.CreatingCommentDto;
import threads.server.domain.follow.dto.FollowingDto;
import threads.server.domain.like.LikeType;
import threads.server.domain.like.dto.CreatingLikeDto;
import threads.server.domain.like.dto.LikeDto;
import threads.server.domain.reply.dto.CreatingReplyDto;

@AllArgsConstructor
@Getter
public class SaveActivityDto {
    private Long toUserId;
    private Long fromUserId;
    private Long targetId;
    private String content;
    private ActivityStatus status;

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    private SaveActivityDto(CreatingCommentDto commentDto) {
        this.toUserId = commentDto.getPostUserId();
        this.fromUserId = commentDto.getUserId();
        this.targetId = commentDto.getPostId();
        this.content = commentDto.getContent();
        this.status = ActivityStatus.COMMENT;
    }
    static public SaveActivityDto getCreatingCommentActivity(CreatingCommentDto commentDto) {
        return new SaveActivityDto(commentDto);
    }

    private SaveActivityDto(FollowingDto followingDto) {
        this.toUserId = followingDto.getToUserId();
        this.fromUserId = followingDto.getFromUserId();
        this.targetId = followingDto.getToUserId();
        this.content = null;
        this.status = ActivityStatus.COMMENT;
    }
    static public SaveActivityDto getFollowingActivity(FollowingDto followDto) {
        return new SaveActivityDto(followDto);
    }

    private SaveActivityDto(CreatingLikeDto likeDto) {
        this.toUserId = likeDto.getTargetUserId();
        this.fromUserId = likeDto.getUserId();
        this.targetId = likeDto.getTargetId();
        this.content = null;
        this.status = likeDto.getType().equals(LikeType.POST)
                ? ActivityStatus.LIKE_POST
                : likeDto.getType().equals(LikeType.COMMENT)
                ? ActivityStatus.LIKE_COMMENT
                : ActivityStatus.LIKE_REPLY;
    }
    static public SaveActivityDto getCreatingLikeActivity(CreatingLikeDto likeDto) {
        return new SaveActivityDto(likeDto);
    }

    private SaveActivityDto(CreatingReplyDto replyDto) {
        this.toUserId = replyDto.getCommentUserId();
        this.fromUserId = replyDto.getUserId();
        this.targetId = replyDto.getCommentId();
        this.content = replyDto.getContent();
        this.status = ActivityStatus.REPLY;
    }

    static public SaveActivityDto getCreatingReplyActivity(CreatingReplyDto replyDto) {
        return new SaveActivityDto(replyDto);
    }
}
