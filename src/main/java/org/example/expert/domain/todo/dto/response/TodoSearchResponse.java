package org.example.expert.domain.todo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class TodoSearchResponse {

    private String title;
    private String nickname;
    private Long managerCount;
    private Long commentCount;

    public TodoSearchResponse(String title, String nickname , long managerCount, long commentCount) {
        this.title = title;
        this.nickname = nickname;
        this.managerCount = managerCount;
        this.commentCount = commentCount;
    }
}
