package com.humberto.api.videostream.domain.video;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;

public record VideoMetadataDTO(
        @NotEmpty
        String title,
        @NotEmpty
        String synopsis,
        @NotEmpty
        String director,
        @NotEmpty
        String actors,
        @Enumerated(EnumType.STRING)
        Genre genre,
        @Column(name = "RUNNING_TIME")
        Integer runningTime
) {
}
