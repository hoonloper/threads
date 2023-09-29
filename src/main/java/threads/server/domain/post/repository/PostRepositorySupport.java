package threads.server.domain.post.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
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

import static threads.server.domain.comment.QComment.comment;
import static threads.server.domain.like.entity.QLikePost.likePost;
import static threads.server.domain.post.QPost.post;

@Repository
public class PostRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public PostRepositorySupport(JPAQueryFactory queryFactory) {
        super(Post.class);
        this.queryFactory = queryFactory;
    }

    public PageImpl<Post> findPostPageByUserId(Pageable pageable, Long userId){

        JPAQuery<Post> query = queryFactory
                .selectFrom(post)
                .where(post.user.id.eq(userId));
        long totalCount = query.fetchCount();
        List<Post> result = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(result, pageable, totalCount);
    }

    public List<PostDto> findAllPostsByUserId(Pageable pageable, Long userId) {
        // TODO: 좋아요 여부 가져오는 쿼리로 개선
        // TODO: replies에서 최신 요소 한개 찾아오는 쿼리로 개선
        return queryFactory
                .select(
                        Projections.bean(
                                PostDto.class,
                                post.id,
                                post.content,
                                post.createdAt,
                                post.lastModifiedAt,
                                post.user.as("userEntity"),
                                comment.countDistinct().as("commentCount"),
                                likePost.countDistinct().as("likeCount")
                        )
                )
                .from(post)
                .where(post.user.id.eq(userId))
                .innerJoin(post.user)
                .leftJoin(post.likePosts, likePost)
                .leftJoin(post.comments, comment)
                .groupBy(post.id, likePost.post.id)
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
                        return new OrderSpecifier(order, post.createdAt);
                    }

                    return new OrderSpecifier(order, post.createdAt); // 디폴트
                })
                .toList();
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
