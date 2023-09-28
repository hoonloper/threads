package threads.server.domain.reply.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import threads.server.domain.comment.Comment;
import threads.server.domain.reply.dto.ReplyDto;
import threads.server.domain.user.dto.UserDto;

import static threads.server.domain.like.entity.QLikeReply.likeReply;
import static threads.server.domain.reply.QReply.reply;

@Repository
public class ReplyRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public ReplyRepositorySupport(JPAQueryFactory queryFactory) {
        super(Comment.class);
        this.queryFactory = queryFactory;
    }

    public ReplyDto findOneByCommentId(Long commentId, Long userId) {
        // TODO: 좋아요 여부 가져오는 쿼리로 개선해야 함
        ReplyDto replyDto = queryFactory
                .select(
                        Projections.bean(
                                ReplyDto.class,
                                Expressions.asNumber(commentId).as("commentId"),
                                reply.id,
                                reply.content,
                                reply.createdAt,
                                reply.lastModifiedAt,
                                reply.user.as("userEntity"),
                                likeReply.count().as("likeCount")
                        )
                )
                .from(reply)
                .where(reply.comment.id.eq(commentId))
                .innerJoin(reply.user)
                .leftJoin(reply.likeReplies, likeReply)
                .groupBy(reply.id)
                .orderBy(reply.createdAt.asc())
                .limit(1)
                .fetchOne();
        if(replyDto != null) {
            replyDto.setUser(UserDto.toDto(replyDto.getUserEntity()));
            Boolean liked = queryFactory
                        .selectFrom(likeReply)
                        .where(likeReply.reply.id.eq(replyDto.getId()).and(Expressions.asBoolean(likeReply.user.id.eq(userId))))
                        .fetchOne() != null;
            replyDto.setLiked(liked);
        }
        return replyDto;
    }
}
