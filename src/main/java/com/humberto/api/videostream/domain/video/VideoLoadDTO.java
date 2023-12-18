package com.humberto.api.videostream.domain.video;

public record VideoLoadDTO(
        String title,

        String synopsis,

        String director,

        String actors,
        Genre genre,
        Integer runningTime,

        byte[] content
) {
    public VideoLoadDTO(Video video){
        this( video.getTitle(), video.getSynopsis(),video.getDirector(),video.getActors(), video.getGenre(), video.getRunningTime(), video.getContent());
    }
}
