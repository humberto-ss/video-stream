package com.humberto.api.videostream.domain.videoContent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoContentRepository extends JpaRepository<VideoContent, Integer> {

    VideoContent findByName(String name);
}
