package com.humberto.api.videostream.controller;

import com.humberto.api.videostream.domain.video.VideoMetadataDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/api/video")
public class VideoController {

    @GetMapping(name = "")
    public ResponseEntity<VideoMetadataDTO> getVideoMetadata(){

    return ResponseEntity.noContent().build();
    }
}
