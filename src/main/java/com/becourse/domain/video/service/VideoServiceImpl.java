package com.becourse.domain.video.service;

import com.becourse.domain.user.domain.UserEntity;
import com.becourse.domain.user.domain.repository.UserRepository;
import com.becourse.domain.video.domain.VideoEntity;
import com.becourse.domain.video.domain.repository.VideoRepository;
import com.becourse.domain.video.dto.req.CreateVideoRequest;
import com.becourse.domain.video.exception.VideoExceptions;
import com.becourse.domain.video.exception.error.VideoError;
import com.becourse.global.common.BaseResponse;
import com.becourse.global.infra.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService{

    private final S3Uploader s3Uploader;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    @Override
    public BaseResponse createVideo(CreateVideoRequest createVideoRequest,
                                    MultipartFile multipartFile,
                                    Authentication authentication) throws IOException {

        if (multipartFile.isEmpty()) throw VideoExceptions.videoNotFoundException();

        else {
            String url = s3Uploader.upload(multipartFile, "videos");

            //authentication.getName() == id (pk, string)
            Optional<UserEntity> user = userRepository.findById(Long.valueOf(authentication.getName()));

            videoRepository.save(dtoToEntity(createVideoRequest, url, user.get().getUserName(), user.get().getId()));

            return new BaseResponse(HttpStatus.OK, "생성성공");
        }
    }

    @Override
    public BaseResponse getVideos() {
        return new BaseResponse(
                HttpStatus.OK,
                "비디오 불러오기 성공",
                videoRepository.findAll().stream().map(this::entityToDto)
        );
    }

    @Override
    public BaseResponse getVideo(Long id) {
        return new BaseResponse(
                HttpStatus.OK,
                "비디오 불러오기 성공",
                entityToDto(videoRepository.findById(id).orElseThrow(VideoExceptions::videoNotFoundException))
        );
    }

    @Override
    public BaseResponse deleteVideo(Long id, Authentication authentication) {
        VideoEntity video = videoRepository.findById(id).orElseThrow(VideoExceptions::videoNotFoundException);
        String info = video.getCheck_info().toString();

        if (info.equals(authentication.getName())){
            videoRepository.deleteById(id);
        } else {
            throw new VideoExceptions(VideoError.VIDEO_NO_ACCESS_ERROR);
        }

        return new BaseResponse(
                HttpStatus.OK,
                "비디오 삭제 성공"
        );
    }
}
