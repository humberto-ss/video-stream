package com.humberto.api.videostream.domain.video;

public record VideoDetailsDTO(

            String title,
            String synopsis,

            String director,

            String actors,

            Genre genre,

            Integer runningTime
) {
        public VideoDetailsDTO(Video video){
            this(video.getTitle(), video.getSynopsis(),video.getDirector(),video.getActors(), video.getGenre(), video.getRunningTime());
        }

}
