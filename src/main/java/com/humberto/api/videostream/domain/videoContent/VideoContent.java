package com.humberto.api.videostream.domain.videoContent;

import com.humberto.api.videostream.domain.videoDetails.VideoDetails;
import jakarta.persistence.*;
import jdk.jfr.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Entity(name = "VIDEO_CONTENT")
@Table(name = "VIDEO_CONTENT")
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String contentType;

    private byte[] content;
    @OneToOne(mappedBy = "videoContent", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    private VideoDetails videoDetails;


}
