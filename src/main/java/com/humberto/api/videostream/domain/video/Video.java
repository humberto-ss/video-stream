package com.humberto.api.videostream.domain.video;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

@Entity(name = "VIDEO")
@Table(name = "VIDEO")
public record Video(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        Integer runningTime,

        String content,
        Integer views,
        @Version
        Integer version

) {

}
