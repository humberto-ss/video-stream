package com.humberto.api.videostream.domain.video;

public record VideoUpdateDetailsDTO (
        Integer id,

        String title,
        String synopsis,

        String director,

        String actors,

        Genre genre,

        Integer runningTime
) {
    public VideoUpdateDetailsDTO(Video video){
        this(video.getId(), video.getTitle(), video.getSynopsis(),video.getDirector(),video.getActors(), video.getGenre(), video.getRunningTime());
    }
}
