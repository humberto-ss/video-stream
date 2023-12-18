package com.humberto.api.videostream.controller;

import com.humberto.api.videostream.domain.video.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RestController
@RequestMapping( "/api/video")
public class VideoController {

    @Autowired
    private VideoRepository repository;

    @GetMapping("/director/{director}")
    public ResponseEntity<Page<VideoDetailsDTO>> getVideosByDirector(@PathVariable String director, @PageableDefault(size = 10, sort = {"title"}) Pageable page){
        var pageVideos = repository.findAllByDirector(director, page).map(VideoDetailsDTO::new);
        return ResponseEntity.ok(pageVideos);
    }

    @GetMapping
    public ResponseEntity<Page<VideoDetailsDTO>> list(@PageableDefault(size = 10, sort = {"title"}) Pageable page){
       var pageList = repository.findAllByDeletedFalse(page).map(VideoDetailsDTO::new);
        return ResponseEntity.ok(pageList);
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<VideoLoadDTO> loadVideo(@PathVariable Integer id){
        var video = repository.findById(id);
        return ResponseEntity.ok(new VideoLoadDTO(video.get()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<VideoDetailsDTO> getVideo(@PathVariable Integer id){
        var video = repository.findById(id);
        return ResponseEntity.ok(new VideoDetailsDTO(video.get()));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<VideoUpdateDetailsDTO> updateVideoDetails(@RequestBody VideoUpdateDetailsDTO videoUpdateDetailsDTO){
        var video = repository.getReferenceById(videoUpdateDetailsDTO.id());
        video.updateDetails(videoUpdateDetailsDTO);
        return ResponseEntity.ok(new VideoUpdateDetailsDTO(video));
    }


    @PostMapping("details")
    @Transactional
    public ResponseEntity addVideoDetails(@RequestBody @Valid VideoCreateDTO data, UriComponentsBuilder uriBuilder) {
        var video = new Video(data);
        repository.save(video);
        var uri = uriBuilder.path("/api/video/{id}").buildAndExpand(video.getId()).toUri();
        return ResponseEntity.created(uri).body(new VideoCreateDTO(video));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Integer id){
        var video =  repository.getReferenceById(id);
        video.delete();
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/pushVideo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<VideoLoadDTO> pushVideo(@RequestParam("file") MultipartFile file) throws IOException {
       Video video = new Video();
       video.setContent(file.getBytes());
       return ResponseEntity.ok().build();
    }

}
