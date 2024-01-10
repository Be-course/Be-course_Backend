package com.becourse.domain.video.presentation;

import com.becourse.domain.video.dto.req.CreateVideoRequest;
import com.becourse.domain.video.service.VideoService;
import com.becourse.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/create")
    public BaseResponse createVideo(@RequestPart("data") CreateVideoRequest createVideoRequest,
                                    @RequestPart("file")MultipartFile multipartFile,
                                    Authentication authentication) throws IOException {
        return videoService.createVideo(createVideoRequest, multipartFile, authentication);
    }

    @GetMapping("/list")
    public BaseResponse getVideos(){
        return videoService.getVideos();
    }

    @GetMapping("/{id}")
    public BaseResponse getVideo(@PathVariable Long id){
        return videoService.getVideo(id);
    }

    @DeleteMapping("/{id}")
    public BaseResponse deleteVideo(@PathVariable Long id,
                                    Authentication authentication){
        return videoService.deleteVideo(id, authentication);
    }

}
