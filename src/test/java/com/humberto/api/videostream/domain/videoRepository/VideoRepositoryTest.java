package com.humberto.api.videostream.domain.videoRepository;

import com.humberto.api.videostream.domain.videoContent.VideoContent;
import com.humberto.api.videostream.domain.videoContent.VideoContentRepository;
import com.humberto.api.videostream.domain.videoDetails.Genre;
import com.humberto.api.videostream.domain.videoDetails.VideoDetailRepository;
import com.humberto.api.videostream.domain.videoDetails.VideoDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VideoRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    VideoDetailRepository videoDetailRepository;

    @Autowired
    VideoContentRepository videoContentRepository;

//    @Autowired
//    JdbcConnectionDetails jdbcConnectionDetails;

    @BeforeEach
    public void setUp(){

        VideoDetails videoDetails = new VideoDetails
                (1,
                "Kill Bill",
                "Test",
                "Tarantino",
                "Leonardo di Caprio",
                Genre.ACTION,
                120,
                0,
                false,
                null,
                0
                );
        var videoContent = new VideoContent(1,"video_test.mp4","video/mp4","Video Content".getBytes(), videoDetails);
        videoDetails.setVideoContent(videoContent);
        videoContentRepository.save(videoContent);
    }
    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }
    @Test
    void shouldReturnVideosByDirector(){
        Pageable pageable = PageRequest.of(0,10 );
        var page  = videoDetailRepository.findAllByDirector("Tarantino",pageable);
        assertThat(page.stream().findAny().isPresent()).isTrue();
        assertThat(page.stream().anyMatch(p-> p.getDirector().equalsIgnoreCase("Tarantino"))).isTrue();
    }
    @Test
    void shouldReturnVideosNotDeleted(){
        Pageable pageable = PageRequest.of(0,10 );
        var page  = videoDetailRepository.findAllByDeletedFalse(pageable);
        assertThat(page.stream().findAny().isPresent()).isTrue();
        assertThat(page.stream().noneMatch(VideoDetails::isDeleted)).isTrue();
    }


}
