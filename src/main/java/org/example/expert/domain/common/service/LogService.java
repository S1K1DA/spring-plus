package org.example.expert.domain.common.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.entity.Log;
import org.example.expert.domain.common.entity.LogStatus;
import org.example.expert.domain.common.repository.LogRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(String message, LogStatus status) {

        Log log = Log.builder()
                .message(message)
                .status(status)
                .createdAt(LocalDateTime.now())
                .build();

        logRepository.save(log);
    }
}