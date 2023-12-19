package com.humberto.api.videostream.domain.videoDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoDetailRepository extends JpaRepository<VideoDetails, Integer> {

    Page<VideoDetails> findAllByDirector(String Director, Pageable pageable);

    Page<VideoDetails> findAllByDeletedFalse (Pageable page);

}
