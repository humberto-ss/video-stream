package com.humberto.api.videostream.domain.videoContent;

public record VideoContentDTO(
        String name,
        String contentType,
        byte[] content
) {
    public VideoContentDTO(VideoContent  videoContent){
        this(videoContent.getName(), videoContent.getContentType(), videoContent.getContent());
    }
}
