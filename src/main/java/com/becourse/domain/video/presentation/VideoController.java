package com.becourse.domain.video.presentation;

import com.becourse.domain.video.dto.req.CreateVideoRequest;
import com.becourse.domain.video.dto.res.VideoDto;
import com.becourse.domain.video.service.VideoService;
import com.becourse.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
@Tag(name = "Video", description = "비디오 생성, 삭제, 불러오기 API")
public class VideoController {

    private final VideoService videoService;

    @Operation(summary = "생성", description = "비디오 생성 (인증된 유저만)")
    @PostMapping("/create")
    public BaseResponse createVideo(@RequestPart("data") CreateVideoRequest createVideoRequest,
                                    @RequestPart("file")MultipartFile multipartFile,
                                    Authentication authentication) throws IOException {
        return videoService.createVideo(createVideoRequest, multipartFile, authentication);
    }

    @ApiResponses(value = {
            @ApiResponse(content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = VideoDto.class)))
            })
    })
    @Operation(summary = "여러 비디오 조회",description = "비디오 전부 가져오기")
    @GetMapping("/list")
    public BaseResponse getVideos(){
        return videoService.getVideos();
    }

    @ApiResponses(value = {
            @ApiResponse(content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = VideoDto.class)))
            })
    })
    @Operation(summary = "비디오 단건 조회", description = "비디오 단건 조회")
    @GetMapping("/{id}")
    public BaseResponse getVideo(@PathVariable Long id){
        return videoService.getVideo(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "삭제" , description = "비디오 삭제하기 (인증된 유저만)")
    public BaseResponse deleteVideo(@PathVariable Long id,
                                    Authentication authentication){
        return videoService.deleteVideo(id, authentication);
    }

}
