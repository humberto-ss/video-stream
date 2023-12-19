package com.humberto.api.videostream.domain.videoDetails;

import com.humberto.api.videostream.domain.videoContent.VideoContent;
import com.humberto.api.videostream.domain.videoContent.VideoContentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VideoDetailsTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    VideoDetailRepository videoDetailRepository;

    @Autowired
    VideoContentRepository videoContentRepository;

    @Autowired
    JdbcConnectionDetails jdbcConnectionDetails;

    @BeforeEach
    void setUp(){

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

}
