package com.REST.taskTracker.domain.dto;

import java.util.List;
import java.util.UUID;

public record TaksListDto(
        UUID id,
        String title,
        String description,
        Integer count,
        Double progress,
        List<TaksDto> tasks) {

}
