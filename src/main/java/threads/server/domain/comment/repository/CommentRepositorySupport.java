package threads.server.domain.comment.repository;


import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import threads.server.domain.comment.Comment;
import threads.server.domain.comment.dto.CommentDto;
import threads.server.domain.reply.QReply;
import threads.server.domain.reply.Reply;
import threads.server.domain.user.dto.UserDto;

import java.util.List;

import static threads.server.domain.comment.QComment.comment;
import static threads.server.domain.like.entity.QLikeComment.likeComment;
import static threads.server.domain.post.QPost.post;
import static threads.server.domain.reply.QReply.reply;

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
        // TODO: replies에서 최신 요소 한개 찾아오는 쿼리로 개선
        return queryFactory
                .select(
                        Projections.bean(
                                CommentDto.class,
                                comment.id,
                                comment.content,
                                comment.createdAt,
                                comment.lastModifiedAt,
                                comment.user.as("userEntity"),
                                comment.likeComments.isNotEmpty().as("liked"),
                                likeComment.count().as("likeCount")
//                                ExpressionUtils.as(
//                                        JPAExpressions
//                                                .selectFrom(reply)
//                                                .where(reply.comment.eq(comment).and(reply.createdAt.eq(reply.createdAt.min())))
//                                                .orderBy(reply.createdAt.asc())
//                                        "replyEntity")
                        )
                )
                .from(comment)
                .innerJoin(comment.user)
                .leftJoin(comment.likeComments, likeComment)
                .where(comment.post.id.eq(postId))
                .groupBy(comment.id)
                .orderBy(comment.createdAt.desc())
                .offset((long) pageable.getPageNumber() * pageable.getPageSize())  // Offset 계산
                .limit(pageable.getPageSize())
                .fetch();
    }

}
