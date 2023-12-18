package com.humberto.api.videostream.domain.video;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Entity(name = "VIDEO")
@Table(name = "video")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Video{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @NotEmpty
        private String title;
        @NotEmpty
        private String synopsis;
        @NotEmpty
        private String director;
        @NotEmpty
        private String actors;
        @Enumerated(EnumType.STRING)
        private Genre genre;
        @Column(name = "RUNNING_TIME")
        private Integer runningTime;

        private byte[] content;
        private Integer views;
        private boolean deleted;
        @Version
        private Integer version;


        public Video(VideoCreateDTO videoCreateDTO){
                this.title = videoCreateDTO.title();
                this.synopsis = videoCreateDTO.synopsis();
                this.director = videoCreateDTO.director();
                this.actors = videoCreateDTO.actors();
                this.genre = videoCreateDTO.genre();
                this.runningTime = videoCreateDTO.runningTime();
                this.deleted = false;
                this.views = 0;
        }

        public void updateDetails(VideoUpdateDetailsDTO videoUpdateDetailsDTO) {
                if(videoUpdateDetailsDTO.title()!=null){
                        this.title = videoUpdateDetailsDTO.title();
                }
                if(videoUpdateDetailsDTO.synopsis() !=null){
                        this.synopsis = videoUpdateDetailsDTO.synopsis();
                }
                if(videoUpdateDetailsDTO.director() !=null ){
                        this.actors = videoUpdateDetailsDTO.actors();
                }
                if(videoUpdateDetailsDTO.genre()!=null){
                        this.genre = videoUpdateDetailsDTO.genre();
                }
                if(videoUpdateDetailsDTO.runningTime() != null){
                        this.runningTime = videoUpdateDetailsDTO.runningTime();
                }
        }
        public void delete(){
                this.setDeleted(true);
        }
}
