package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.request.TodoSearchRequest;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;


@RequiredArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {

        Todo result = queryFactory
                .selectFrom(todo)
                .join(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<TodoSearchResponse> searchTodos(TodoSearchRequest request, Pageable pageable) {

        QUser author = new QUser("author"); // 작성자

        List<TodoSearchResponse> content = queryFactory
                .select(Projections.constructor(
                        TodoSearchResponse.class,
                        todo.title,
                        author.nickname,
                        manager.id.countDistinct(),
                        comment.id.countDistinct()
                ))
                .from(todo)
                .join(todo.user, author)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .where(
                        titleContains(request.getKeyword()),
                        authorNicknameContains(request.getNickname(), author),
                        createdDateBetween(request.getStartDate(), request.getEndDate())
                )
                .groupBy(todo.id, todo.title, author.nickname)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(todo.id.countDistinct())
                .from(todo)
                .join(todo.user, author)
                .leftJoin(todo.managers, manager)
                .where(
                        titleContains(request.getKeyword()),
                        authorNicknameContains(request.getNickname(), author),
                        createdDateBetween(request.getStartDate(), request.getEndDate())
                )
                .fetchOne();

        return PageableExecutionUtils.getPage(content, pageable, () -> total == null ? 0L : total);
    }

    private BooleanExpression authorNicknameContains(String nickname, QUser author) {
        return nickname == null || nickname.isBlank()
                ? null
                : author.nickname.contains(nickname);
    }

    private BooleanExpression titleContains(String title) {
        return title == null || title.isBlank() ? null : todo.title.contains(title);
    }

    private BooleanExpression createdDateBetween(LocalDateTime start, LocalDateTime end) {
        if (start != null && end != null) return todo.createdAt.between(start, end);
        if (start != null) return todo.createdAt.goe(start);
        if (end != null) return todo.createdAt.loe(end);
        return null;
    }
}

