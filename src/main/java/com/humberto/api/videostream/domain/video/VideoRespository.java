package com.humberto.api.videostream.domain.video;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRespository extends JpaRepository<Video, Integer> {
}
