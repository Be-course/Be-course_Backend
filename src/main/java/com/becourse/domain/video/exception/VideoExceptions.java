package com.becourse.domain.video.exception;

import com.becourse.domain.video.exception.error.VideoError;
import com.becourse.global.exception.BusinessException;
import com.becourse.global.exception.error.ErrorProperty;

import static com.becourse.domain.video.exception.error.VideoError.VIDEO_NO_ACCESS_ERROR;

public class VideoExceptions extends BusinessException {
    private static final VideoExceptions VIDEO_NOT_FOUND_EXCEPTION = new VideoExceptions(VideoError.VIDEO_NOT_FOUND);

    private static final VideoExceptions VIDEO_EXCEPTIONS = new VideoExceptions(VIDEO_NO_ACCESS_ERROR);
    public VideoExceptions(ErrorProperty error) {
        super(error);
    }

    public static VideoExceptions videoNotFoundException() {
        return VIDEO_NOT_FOUND_EXCEPTION;
    }
    public static VideoExceptions videoNoAccessError(){return VIDEO_EXCEPTIONS;}
}
