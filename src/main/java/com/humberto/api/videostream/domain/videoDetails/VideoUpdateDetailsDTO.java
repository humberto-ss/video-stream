package com.humberto.api.videostream.domain.videoDetails;

public record VideoUpdateDetailsDTO (
        Integer id,

        String title,
        String synopsis,

        String director,

        String actors,

        Genre genre,

        Integer runningTime
) {
    public VideoUpdateDetailsDTO(VideoDetails video){
        this(video.getId(), video.getTitle(), video.getSynopsis(),video.getDirector(),video.getActors(), video.getGenre(), video.getRunningTime());
    }
}
