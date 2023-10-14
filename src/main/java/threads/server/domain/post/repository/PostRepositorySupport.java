package threads.server.domain.post.repository;

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
import threads.server.domain.post.Post;
import threads.server.domain.post.dto.PostDto;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static threads.server.domain.comment.QComment.comment;
import static threads.server.domain.like.entity.QLikePost.likePost;
import static threads.server.domain.post.QPost.post;
import static threads.server.domain.user.QUser.user;

@Repository
public class PostRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public PostRepositorySupport(JPAQueryFactory queryFactory) {
        super(Post.class);
        this.queryFactory = queryFactory;
    }

    public PageImpl<Post> findPostPage(Pageable pageable, Optional<Long> userId){
        JPAQuery<Post> query = queryFactory.selectFrom(post);
        userId.ifPresent(id -> query.where(post.user.id.eq(id)));
        long totalCount = query.fetchCount();
        List<Post> result = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();

        return new PageImpl<>(result, pageable, totalCount);
    }

    public Optional<PostDto> findById(Long postId, Long userId) {
        return Optional.ofNullable(defaultJoinQuery(userId, post.id.eq(postId)).fetchOne());
    }

    public List<PostDto> findAllPosts(Pageable pageable, Long userId) {
        return defaultJoinQuery(userId, pageable).fetch();
    }

    public List<PostDto> findAllPostsByUserId(Pageable pageable, Long userId) {
        return defaultJoinQuery(userId, post.user.id.eq(userId), pageable).fetch();
    }

    private JPAQuery<PostDto> defaultJoinQuery(Long userId) {
        // TODO: replies에서 최신 요소 한개 찾아오는 쿼리로 개선
        return queryFactory
                .select(makePostDtoBean(userId))
                .from(post)
                .innerJoin(post.user, user)
                .leftJoin(post.likePosts, likePost)
                .leftJoin(post.comments, comment)
                .groupBy(post.id);
    }

    private JPAQuery<PostDto> defaultJoinQuery(Long userId, BooleanExpression where) {
        return defaultJoinQuery(userId).where(where);
    }

    private JPAQuery<PostDto> defaultJoinQuery(Long userId, Pageable pageable) {
        return defaultJoinQuery(userId)
                .orderBy(createOrderSpecifier(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
    }

    private JPAQuery<PostDto> defaultJoinQuery(Long userId, BooleanExpression where, Pageable pageable) {
        return defaultJoinQuery(userId, where)
                .orderBy(createOrderSpecifier(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
    }


    private QBean<PostDto> makePostDtoBean(Long userId) {
        return Projections.bean(
                PostDto.class,
                post.id,
                post.content,
                post.createdAt,
                post.lastModifiedAt,
                Expressions.asNumber(post.user.id).as("userId"),
                post.user.as("userEntity"),
                comment.countDistinct().as("commentCount"),
                likePost.countDistinct().as("likeCount"),
                ExpressionUtils.as(Expressions.booleanTemplate("exists(select 1 from LikePost l where l.post.id = {0} and l.user.id = {1})", post.id, userId), "liked")
        );
    }

    private OrderSpecifier[] createOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orderSpecifiers = sort.stream()
                .map(s -> {
                    Order order = Order.valueOf(String.valueOf(s.getDirection()));
                    if(s.getProperty().equals("createdAt")) {
                        return new OrderSpecifier(order, post.createdAt);
                    }

                    return new OrderSpecifier(order, post.createdAt); // 디폴트
                })
                .toList();
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
