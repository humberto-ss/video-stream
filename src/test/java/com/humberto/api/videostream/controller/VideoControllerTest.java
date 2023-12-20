package com.humberto.api.videostream.controller;

import com.humberto.api.videostream.domain.videoContent.VideoContent;
import com.humberto.api.videostream.domain.videoContent.VideoContentRepository;
import com.humberto.api.videostream.domain.videoDetails.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class VideoControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    VideoContentRepository videoContentRepository;
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
    void shouldCreateVideo() throws IOException {
        var multiPart = createMultiPartFile();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ResponseEntity<VideoLoadDTO> response = restTemplate.exchange("/api/video/push",HttpMethod.POST, new HttpEntity<>(multiPart,headers), VideoLoadDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void shouldNotAddDetails(){
        VideoCreateDTO videoCreateDTO = new VideoCreateDTO
                (
                999,
                "Lord of the rings",
                "Test",
                "Pert Jackson",
                "Test",
                Genre.FICTION,
                120
                );
       ResponseEntity response = restTemplate.exchange("/api/video/details",HttpMethod.POST, new HttpEntity<>(videoCreateDTO) , Void.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private MultiValueMap createMultiPartFile() throws IOException {
        var mockFile = new MockMultipartFile(
                "file",
                "test.mp4",
                "video/mp4",
                "filecontent".getBytes()
        );

        MultiValueMap<String, Object> multiPart = new LinkedMultiValueMap<>();
        multiPart.add("file", new ByteArrayResource(mockFile.getBytes()) {
            @Override
            public String getFilename() {
                return mockFile.getOriginalFilename();
            }
        });
        return multiPart;
    }
}
