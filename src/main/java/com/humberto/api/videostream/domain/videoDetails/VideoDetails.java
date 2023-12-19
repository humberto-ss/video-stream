package com.humberto.api.videostream.domain.videoDetails;

import com.humberto.api.videostream.domain.videoContent.VideoContent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Entity(name = "VIDEO_DETAILS")
@Table(name = "VIDEO_DETAILS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class VideoDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String title;

        private String synopsis;

        private String director;

        private String actors;
        @Enumerated(EnumType.STRING)
        private Genre genre;
        @Column(name = "RUNNING_TIME")
        private Integer runningTime;

        private Integer views;

        private boolean deleted;

        @OneToOne
        @MapsId
        @JoinColumn(name="id")
        private VideoContent videoContent;

        @Version
        private Integer version;


        public VideoDetails(VideoCreateDTO videoCreateDTO){
                this.title = videoCreateDTO.title();
                this.synopsis = videoCreateDTO.synopsis();
                this.director = videoCreateDTO.director();
                this.actors = videoCreateDTO.actors();
                this.genre = videoCreateDTO.genre();
                this.runningTime = videoCreateDTO.runningTime();
                this.deleted = false;
                this.views = 0;
        }


}
