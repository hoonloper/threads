package threads.server.domain.reply.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import threads.server.domain.comment.dto.CommentDto;
import threads.server.domain.reply.Reply;
import threads.server.domain.reply.dto.ReplyDto;
import threads.server.domain.user.dto.UserDto;

import java.util.List;

import static threads.server.domain.like.entity.QLikeReply.likeReply;
import static threads.server.domain.reply.QReply.reply;

@Repository
public class ReplyRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public ReplyRepositorySupport(JPAQueryFactory queryFactory) {
        super(Reply.class);
        this.queryFactory = queryFactory;
    }


    public PageImpl<Reply> findReplyPage(Pageable pageable, Long commentId){

        JPAQuery<Reply> query = queryFactory.selectFrom(reply).where(reply.comment.id.eq(commentId));
        long totalCount = query.fetchCount();
        List<Reply> result = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(result, pageable, totalCount);
    }

    public List<ReplyDto> findAllReplies(Pageable pageable, Long commentId, Long userId) {
        // TODO: 좋아요 여부 가져오는 쿼리로 개선
        return queryFactory
                .select(
                        Projections.bean(
                                ReplyDto.class,
                                reply.id,
                                Expressions.asNumber(commentId).as("commentId"),
                                reply.content,
                                reply.createdAt,
                                reply.lastModifiedAt,
                                reply.user.as("userEntity"),
                                reply.countDistinct().as("replyCount"),
                                likeReply.countDistinct().as("likeCount")
                        )
                )
                .from(reply)
                .where(reply.comment.id.eq(commentId))
                .innerJoin(reply.user)
                .leftJoin(reply.likeReplies, likeReply)
                .groupBy(reply.id, likeReply.reply.id)
                .orderBy(createOrderSpecifier(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private OrderSpecifier[] createOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orderSpecifiers = sort.stream()
                .map(s -> {
                    Order order = Order.valueOf(String.valueOf(s.getDirection()));
                    if(s.getProperty().equals("createdAt")) {
                        return new OrderSpecifier(order, reply.createdAt);
                    }

                    return new OrderSpecifier(order, reply.createdAt); // 디폴트
                })
                .toList();
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
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
