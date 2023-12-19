package com.humberto.api.videostream.domain.videoDetails;

import com.humberto.api.videostream.domain.videoContent.VideoContentDTO;

public record VideoLoadDTO(
        String title,

        String synopsis,

        String director,

        String actors,
        Genre genre,
        Integer runningTime,

        VideoContentDTO videoContentDTO
) {
    public VideoLoadDTO(VideoDetails video, VideoContentDTO videoContentDTO){
        this( video.getTitle(), video.getSynopsis(),video.getDirector(),video.getActors(), video.getGenre(), video.getRunningTime(), videoContentDTO);
    }
}
