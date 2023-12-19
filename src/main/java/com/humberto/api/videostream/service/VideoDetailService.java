package com.humberto.api.videostream.service;

import com.humberto.api.videostream.domain.videoContent.VideoContent;
import com.humberto.api.videostream.domain.videoContent.VideoContentDTO;
import com.humberto.api.videostream.domain.videoDetails.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VideoDetailService {
    private final VideoDetailRepository repository;


    public VideoDetailService(VideoDetailRepository repository) {

        this.repository = repository;
    }

    public VideoDetails create(VideoContent videoStored) {
        var videoDetails = new VideoDetails ();
        videoDetails.setVideoContent(videoStored);
        videoDetails.setDeleted(false);
        videoDetails.setViews(0);
        return videoDetails;

    }

    public Page<VideoDetailsDTO> findAll(String director, Pageable page) {
        return repository.findAllByDirector(director, page).map(VideoDetailsDTO::new);
    }

    public Page<VideoDetailsDTO> listActiveVideos(Pageable page) {
        return repository.findAllByDeletedFalse(page).map(VideoDetailsDTO::new);
    }

    public Optional<VideoDetails> findById(Integer id) {
        return  repository.findById(id);
    }

    @Transactional
    public void delete(VideoContent videoContent) {
        var videoDetails = videoContent.getVideoDetails();
        videoDetails.setDeleted(true);
        repository.save(videoDetails);
    }
    @Transactional
    public VideoDetails addDetails(VideoCreateDTO data, VideoContent videoContent) {
        var videoDetails = videoContent.getVideoDetails();
        videoDetails.setTitle(data.title());
        videoDetails.setDirector(data.director());
        videoDetails.setSynopsis(data.synopsis());
        videoDetails.setActors(data.actors());
        videoDetails.setGenre(data.genre());
        videoDetails.setRunningTime(data.runningTime());
        return repository.save(videoDetails);
    }

    @Transactional
    public VideoUpdateDetailsDTO update(VideoUpdateDetailsDTO videoUpdateDetailsDTO, VideoContent videoContent) {
        var video = videoContent.getVideoDetails();

        if(videoUpdateDetailsDTO.title()!=null){
            video.setTitle(videoUpdateDetailsDTO.title());
        }
        if(videoUpdateDetailsDTO.synopsis() !=null){
            video.setSynopsis(videoUpdateDetailsDTO.synopsis());
        }
        if(videoUpdateDetailsDTO.director() !=null ){
            video.setActors(videoUpdateDetailsDTO.actors());
        }
        if(videoUpdateDetailsDTO.genre()!=null){
            video.setGenre(videoUpdateDetailsDTO.genre());
        }
        if(videoUpdateDetailsDTO.runningTime() != null){
            video.setRunningTime(videoUpdateDetailsDTO.runningTime());
        }
        repository.save(video);
        return new VideoUpdateDetailsDTO(video);
    }

    @Transactional
    public void updateViews(VideoContent videoContent) {
         var videoDetails = videoContent.getVideoDetails();
         videoDetails.setViews(videoDetails.getViews()+1);
         repository.save(videoDetails);
    }

    public VideoLoadDTO loadVideo(VideoContent videoContent) {
        var videoDetail = videoContent.getVideoDetails();
        var videoContentDTO =  new VideoContentDTO(videoContent);

        return new VideoLoadDTO(videoDetail, videoContentDTO);
    }
}
