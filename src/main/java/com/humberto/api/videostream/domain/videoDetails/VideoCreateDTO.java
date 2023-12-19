package com.humberto.api.videostream.domain.videoDetails;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record VideoCreateDTO(
            @NotNull
            Integer id,
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
            @Min(1)
            Integer runningTime

    ) {
            public VideoCreateDTO(VideoDetails video){
                    this(
                            video.getId(),
                            video.getTitle(),
                            video.getSynopsis(),
                            video.getDirector(),
                            video.getActors(),
                            video.getGenre(),
                            video.getRunningTime()

                    );
            }

}
