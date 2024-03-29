package com.becourse.domain.video.service;

import com.becourse.domain.video.domain.VideoEntity;
import com.becourse.domain.video.dto.req.CreateVideoRequest;
import com.becourse.domain.video.dto.res.VideoDto;
import com.becourse.global.common.BaseResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

public interface VideoService {

    BaseResponse createVideo(CreateVideoRequest createVideoRequest, MultipartFile multipartFile, Authentication authentication) throws IOException;

    BaseResponse getVideos();

    BaseResponse getVideo(Long id);

    BaseResponse deleteVideo(Long id, Authentication authentication);

    default VideoEntity dtoToEntity(CreateVideoRequest createVideoRequest, String url, String userName, Long info){
        return VideoEntity.builder()
                .title(createVideoRequest.title())
                .content(createVideoRequest.content())
                .tag(String.join(",", createVideoRequest.tag()))
                .createAt(LocalDate.now())
                .creator(userName)
                .video_url(url)
                .price(createVideoRequest.price())
                .check_info(info)
                .build();

    }
    default VideoDto entityToDto(VideoEntity videoEntity){
        return VideoDto.builder()
                .id(videoEntity.getId())
                .title(videoEntity.getTitle())
                .content(videoEntity.getContent())
                .tag(Arrays.asList(videoEntity.getTag().split(",")))
                .createAt(videoEntity.getCreateAt())
                .creator(videoEntity.getCreator())
                .video_url(videoEntity.getVideo_url())
                .price(videoEntity.getPrice())
                .build();
    }

}
