package com.humberto.api.videostream.controller;

import com.humberto.api.videostream.domain.videoContent.VideoContent;
import com.humberto.api.videostream.domain.videoDetails.*;

import com.humberto.api.videostream.service.VideoContentService;
import com.humberto.api.videostream.service.VideoDetailService;
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
    private VideoDetailService videoDetailService;
    @Autowired
    private VideoContentService videoContentService;
    @GetMapping("/director/{director}")
    public ResponseEntity<Page<VideoDetailsDTO>> getVideosByDirector(@PathVariable String director, @PageableDefault(size = 10, sort = {"title"}) Pageable page){
        var pageVideos = videoDetailService.findAll(director, page);
        return ResponseEntity.ok(pageVideos);
    }

    @GetMapping
    public ResponseEntity<Page<VideoDetailsDTO>> list(@PageableDefault(size = 10, sort = {"title"}) Pageable page){
       var pageList = videoDetailService.listActiveVideos(page);
        return ResponseEntity.ok(pageList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<VideoDetailsDTO> getVideo(@PathVariable Integer id){
        var video = videoDetailService.findById(id);
        return ResponseEntity.ok(new VideoDetailsDTO(video.get()));
    }

    @PostMapping("/load/{id}")
    public ResponseEntity<VideoLoadDTO> loadVideo(@PathVariable Integer id){
        var videoLoadDTO = videoContentService.loadVideo(id);
        return ResponseEntity.ok(videoLoadDTO);
    }


    @GetMapping("/play/{name}")
    public ResponseEntity<?>playVideo(@PathVariable String name){
        var videoContent =  videoContentService.retrieve(name);
        if(videoContent!=null) {
            return ResponseEntity.ok().contentType(MediaType.valueOf(videoContent.getContentType())).body(videoContent.getContent());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<VideoUpdateDetailsDTO> updateVideoDetails(@RequestBody VideoUpdateDetailsDTO videoUpdateDetailsDTO){
        var videoContent = videoContentService.getVideoByID(videoUpdateDetailsDTO.id());
        var video = videoDetailService.update(videoUpdateDetailsDTO,videoContent);
        return ResponseEntity.ok(video);
    }

    @PostMapping("details")
    public ResponseEntity addVideoDetails(@RequestBody @Valid VideoCreateDTO data, UriComponentsBuilder uriBuilder) {
        var videoContent = videoContentService.getVideoByID(data.id());
        var video = videoDetailService.addDetails(data,videoContent);
        var uri = uriBuilder.path("/api/video/{id}").buildAndExpand(video.getId()).toUri();
        return ResponseEntity.created(uri).body(new VideoCreateDTO(video));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        var videoContent = videoContentService.getVideoByID(id);
        videoDetailService.delete(videoContent);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/push", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<VideoLoadDTO> publishVideo(@RequestParam("file") MultipartFile file) throws IOException {
        videoContentService.store(file);
       return ResponseEntity.ok().build();
    }

}
