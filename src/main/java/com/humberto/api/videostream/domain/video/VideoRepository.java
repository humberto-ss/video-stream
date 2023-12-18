package com.humberto.api.videostream.domain.video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Integer> {

    Page<Video> findAllByDirector(String Director, Pageable pageable);

    Page<Video> findAllByDeletedFalse (Pageable page);
}
