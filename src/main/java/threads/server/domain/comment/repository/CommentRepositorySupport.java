package threads.server.domain.comment.repository;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import threads.server.domain.comment.Comment;
import threads.server.domain.comment.dto.CommentDto;

import java.util.List;

import static threads.server.domain.comment.QComment.comment;
import static threads.server.domain.like.entity.QLikeComment.likeComment;
import static threads.server.domain.reply.QReply.reply;

@Repository
public class CommentRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public CommentRepositorySupport(JPAQueryFactory queryFactory) {
        super(Comment.class);
        this.queryFactory = queryFactory;
    }

    public PageImpl<Comment> findCommentPageByPostId(Pageable pageable, Long postId){
        JPAQuery<Comment> query = makeSelectFromQueryWith(comment.post.id.eq(postId));
        return makePageImpl(query, pageable);
    }


    public PageImpl<Comment> findCommentPageByUserId(Pageable pageable, Long userId){
        JPAQuery<Comment> query = makeSelectFromQueryWith(comment.user.id.eq(userId));
        return makePageImpl(query, pageable);
    }

    private PageImpl<Comment> makePageImpl(JPAQuery<Comment> query, Pageable pageable) {
        long totalCount = query.fetchCount();
        List<Comment> result = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(result, pageable, totalCount);
    }

    private JPAQuery<Comment> makeSelectFromQueryWith(BooleanExpression where) {
        return queryFactory.selectFrom(comment).where(where);
    }

    public List<CommentDto> findAllComments(Pageable pageable, Long postId, Long userId) {
        return defaultJoinQuery(userId, comment.post.id.eq(postId))
                .orderBy(createOrderSpecifier(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<CommentDto> findAllCommentsByUserId(Pageable pageable, Long userId) {
        return defaultJoinQuery(userId, comment.user.id.eq(userId))
                .orderBy(createOrderSpecifier(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private JPAQuery<CommentDto> defaultJoinQuery(Long userId) {
        // TODO: replies에서 최신 요소 한개 찾아오는 쿼리로 개선
        return queryFactory
                .select(makePostDtoBean(userId))
                .from(comment)
                .innerJoin(comment.user)
                .leftJoin(comment.likeComments, likeComment)
                .leftJoin(comment.replies, reply)
                .groupBy(comment.id);
    }

    private JPAQuery<CommentDto> defaultJoinQuery(Long userId, BooleanExpression where) {
        return defaultJoinQuery(userId).where(where);
    }

    private QBean<CommentDto> makePostDtoBean(Long userId) {
        return Projections.bean(
                CommentDto.class,
                comment.id,
                comment.post.id.as("postId"),
                comment.content,
                comment.createdAt,
                comment.lastModifiedAt,
                comment.user.as("userEntity"),
                reply.countDistinct().as("replyCount"),
                likeComment.countDistinct().as("likeCount"),
                ExpressionUtils.as(Expressions.booleanTemplate("exists(select 1 from LikeComment l where l.comment.id = {0} and l.user.id = {1})", comment.id, userId), "liked")
        );
    }

    private OrderSpecifier[] createOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orderSpecifiers = sort.stream()
                .map(s -> {
                    Order order = Order.valueOf(String.valueOf(s.getDirection()));
                    if(s.getProperty().equals("createdAt")) {
                        return new OrderSpecifier(order, comment.createdAt);
                    }

                    return new OrderSpecifier(order, comment.createdAt); // 디폴트
                })
                .toList();
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
