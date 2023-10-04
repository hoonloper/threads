package threads.server.domain.user;


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
import threads.server.domain.user.dto.UserDto;

import java.util.Arrays;
import java.util.List;

import static threads.server.domain.user.QUser.user;

@Repository
public class UserRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public UserRepositorySupport(JPAQueryFactory queryFactory) {
        super(User.class);
        this.queryFactory = queryFactory;
    }

    public PageImpl<User> findUserPageByFollowingIds(Pageable pageable, List<Long> followingIds){
        JPAQuery<User> query = queryFactory
                .selectFrom(user)
                .where(user.id.notIn(followingIds));
        long totalCount = query.fetchCount();
        List<User> result = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(result, pageable, totalCount);
    }

    public List<UserDto> findAllUnfollowers(Pageable pageable, List<Long> followingIds) {
        return queryFactory
                .select(
                        Projections.bean(
                                UserDto.class,
                                user.id,
                                user.email,
                                user.name,
                                user.nickname,
                                user.link,
                                user.introduction,
                                user.isHidden,
                                user.userRole,
                                user.createdAt,
                                user.lastModifiedAt
                        )
                )
                .from(user)
                .where(user.id.notIn(followingIds))
                .orderBy(createOrderSpecifier(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public PageImpl<User> findKeywordPageByKeyword(Pageable pageable, String keyword, List<Long> followingIds) {
        JPAQuery<User> query = queryFactory
                .selectFrom(user)
                .where(user.id.notIn(followingIds).and(user.nickname.contains(keyword).or(user.name.contains(keyword))));
        long totalCount = query.fetch().size();
        List<User> result = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(result, pageable, totalCount);
    }

    public List<UserDto> searchByUnfollowers(Pageable pageable, String keyword, List<Long> followingIds) {
        return queryFactory
                .select(
                        Projections.bean(
                                UserDto.class,
                                user.id,
                                user.email,
                                user.name,
                                user.nickname,
                                user.link,
                                user.introduction,
                                user.isHidden,
                                user.userRole,
                                user.createdAt,
                                user.lastModifiedAt
                        )
                )
                .from(user)
                .where(user.id.notIn(followingIds).and(user.nickname.contains(keyword).or(user.name.contains(keyword))))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private OrderSpecifier[] createOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orderSpecifiers = sort.stream()
                .map(s -> {
                    Order order = Order.valueOf(String.valueOf(s.getDirection()));
                    if(s.getProperty().equals("createdAt")) {
                        return new OrderSpecifier(order, user.createdAt);
                    }

                    return new OrderSpecifier(order, user.createdAt); // 디폴트
                })
                .toList();
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

}