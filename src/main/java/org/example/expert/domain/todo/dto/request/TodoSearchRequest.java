package org.example.expert.domain.todo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TodoSearchRequest {

    private String keyword;
    private String nickname;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
