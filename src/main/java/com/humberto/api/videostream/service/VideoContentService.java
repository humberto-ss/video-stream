package com.humberto.api.videostream.service;

import com.humberto.api.videostream.domain.videoContent.VideoContent;
import com.humberto.api.videostream.domain.videoContent.VideoContentRepository;
import com.humberto.api.videostream.domain.videoDetails.VideoDetails;
import com.humberto.api.videostream.domain.videoDetails.VideoLoadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class VideoContentService {
    @Autowired
    VideoContentRepository repository;
    @Autowired
    VideoDetailService videoDetailService;
    @Transactional
    public void store(MultipartFile video){
        var videoContent = new VideoContent();
        try {
            videoContent.setName(video.getOriginalFilename());
            videoContent.setContentType(video.getContentType());

            videoContent.setContent(video.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var videoDetails = videoDetailService.create(videoContent);
        videoContent.setVideoDetails(videoDetails);
        repository.save(videoContent);
    }

    public VideoContent retrieve(String name){
        var videoContent = repository.findByName(name);
        if(videoContent!=null) {
            videoDetailService.updateViews(videoContent);
        }
        return videoContent;
    }

    public VideoContent getVideoByID(Integer id){
        return repository.getReferenceById(id);
    }

    public VideoLoadDTO loadVideo(Integer id) {
        var videoContent = repository.findById(id);
        if(videoContent.isPresent())
            return videoDetailService.loadVideo(videoContent.get());

        return null;
    }
}
