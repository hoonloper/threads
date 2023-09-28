package threads.server.domain.comment.repository;

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
import threads.server.domain.comment.Comment;
import threads.server.domain.comment.dto.CommentDto;

import java.util.List;

import static threads.server.domain.comment.QComment.comment;
import static threads.server.domain.like.entity.QLikeComment.likeComment;

@Repository
public class CommentRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public CommentRepositorySupport(JPAQueryFactory queryFactory) {
        super(Comment.class);
        this.queryFactory = queryFactory;
    }

    public PageImpl<Comment> findCommentPage(Pageable pageable, Long postId){

        JPAQuery<Comment> query = queryFactory.selectFrom(comment).where(comment.post.id.eq(postId));
        long totalCount = query.fetchCount();
        List<Comment> result = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(result, pageable, totalCount);
    }

    public List<CommentDto> findAllComments(Pageable pageable, Long postId, Long userId) {
        // TODO: 좋아요 여부 가져오는 쿼리로 개선
        // TODO: replies에서 최신 요소 한개 찾아오는 쿼리로 개선
        return queryFactory
                .select(
                        Projections.bean(
                                CommentDto.class,
                                comment.id,
                                Expressions.asNumber(postId).as("postId"),
                                comment.content,
                                comment.createdAt,
                                comment.lastModifiedAt,
                                comment.user.as("userEntity"),
                                likeComment.count().as("likeCount")
                        )
                )
                .from(comment)
                .where(comment.post.id.eq(postId))
                .innerJoin(comment.user)
                .leftJoin(comment.likeComments, likeComment)
                .groupBy(comment.id)
                .orderBy(createOrderSpecifier(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private OrderSpecifier[] createOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orderSpecifiers = sort.stream()
                .map(s -> {
                    System.out.println("SORT " + s.toString() + " " + s.getProperty()+ " " + s.getDirection());
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
