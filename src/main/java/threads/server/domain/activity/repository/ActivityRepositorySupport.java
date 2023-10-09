package threads.server.domain.activity.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import threads.server.domain.activity.Activity;
import threads.server.domain.activity.ActivityStatus;
import threads.server.domain.activity.dto.ActivityDto;
import threads.server.domain.user.User;
import threads.server.domain.user.dto.UserDto;

import java.util.List;

import static threads.server.domain.activity.QActivity.activity;
import static threads.server.domain.user.QUser.user;

@Repository
public class ActivityRepositorySupport  extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public ActivityRepositorySupport(JPAQueryFactory queryFactory) {
        super(Activity.class);
        this.queryFactory = queryFactory;
    }


    public PageImpl<Activity> findActivityPageByUserId(Pageable pageable, Long userId, ActivityStatus status) {
        JPAQuery<Activity> query = queryFactory
                .selectFrom(activity)
                .where(activity.toUserId.eq(userId));

        if(status != null) {
            query.where(activity.status.eq(status));
        }

        long totalCount = query.fetchCount();
        List<Activity> result = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(result, pageable, totalCount);
    }

    public List<ActivityDto> findAllActivitiesByUserIdAndStatus(Pageable pageable, Long userId, ActivityStatus status) {
        JPAQuery<ActivityDto> activityJPAQueryFactory = queryFactory
                .select(
                        Projections.bean(
                                ActivityDto.class,
                                activity.id,
                                activity.toUserId,
                                user.as("fromUserEntity"),
                                activity.targetId,
                                activity.content,
                                activity.status,
                                activity.isConfirmed,
                                activity.issuedAt
                        )
                )
                .from(activity)
                .where(activity.toUserId.eq(userId));
        if(status != null) {
            activityJPAQueryFactory.where(activity.status.eq(status));
        }
        return activityJPAQueryFactory
                .leftJoin(user).on(user.id.eq(activity.fromUserId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(activity.issuedAt.desc())
                .fetch();
    }
}
