package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoCustomRepository {


    // weather 조건은 있을 수도 있고, 없을 수도 있음
    // 수정일 기준으로 검색하고 시작/종료 선택 가능
    // 수정일 기준으로 정렬
    // 쿼리 메소드명은 요구사항보고 자유롭게 하면서 짧게 수정
    @Query("""
        SELECT t FROM Todo t
        WHERE (:weather IS NULL OR t.weather = :weather)
          AND (:startDate IS NULL OR t.modifiedAt >= :startDate)
          AND (:endDate IS NULL OR t.modifiedAt <= :endDate)
        ORDER BY t.modifiedAt DESC
    """)
    Page<Todo> searchTodos(
            @Param("weather") String weather,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );


//    @Query("SELECT t FROM Todo t " +
//            "LEFT JOIN t.user " +
//            "WHERE t.id = :todoId")
//    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);
}
