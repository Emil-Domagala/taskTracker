package com.REST.taskTracker.domain.dto;

public record ErrorResponse(
        int status,
        String message,
        String details) {

}
