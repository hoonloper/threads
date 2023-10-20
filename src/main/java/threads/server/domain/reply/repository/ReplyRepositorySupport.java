package threads.server.domain.reply.repository;

import com.querydsl.core.types.*;
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
import java.util.Optional;

import static threads.server.domain.comment.QComment.comment;
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
        return queryFactory
                .select(makeReplyDtoBean(userId))
                .from(reply)
                .where(reply.comment.id.eq(commentId))
                .innerJoin(reply.user)
                .leftJoin(reply.likeReplies, likeReply)
                .groupBy(reply.id, likeReply.reply.id)
                .orderBy(createOrderSpecifier(pageable.getSort()))
                .offset(pageable.getOffset() + 1) // 답글을 더보기 할 때는 이미 최신 요소 1개가 있기 때문에 +1
                .limit(pageable.getPageSize())
                .fetch();
    }

    private QBean<ReplyDto> makeReplyDtoBean(Long userId) {
        return Projections.bean(
                    ReplyDto.class,
                    reply.id,
                    Expressions.asNumber(reply.comment.id).as("commentId"),
                    reply.content,
                    reply.createdAt,
                    reply.lastModifiedAt,
                    reply.user.as("userEntity"),
                    reply.countDistinct().as("replyCount"),
                    likeReply.countDistinct().as("likeCount"),
                    ExpressionUtils.as(Expressions.booleanTemplate("exists(select 1 from LikeReply l where l.reply.id = {0} and l.user.id = {1})", comment.id, userId), "liked")
            );
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

    public Optional<ReplyDto> findOneByCommentId(Long commentId, Long userId) {
         return Optional.ofNullable(queryFactory
                 .select(makeReplyDtoBean(userId))
                 .from(reply)
                 .where(reply.comment.id.eq(commentId))
                 .innerJoin(reply.user)
                 .leftJoin(reply.likeReplies, likeReply)
                 .groupBy(reply.id)
                 .orderBy(reply.createdAt.asc())
                 .limit(1)
                 .fetchOne());
    }
}
