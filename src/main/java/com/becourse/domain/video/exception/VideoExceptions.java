package com.becourse.domain.video.exception;

import com.becourse.domain.video.exception.error.VideoError;
import com.becourse.global.exception.BusinessException;
import com.becourse.global.exception.error.ErrorProperty;

public class VideoExceptions extends BusinessException {
    private static final VideoExceptions VIDEO_NOT_FOUND_EXCEPTION = new VideoExceptions(VideoError.VIDEO_NOT_FOUND);
    public VideoExceptions(ErrorProperty error) {
        super(error);
    }

    public static VideoExceptions videoNotFoundException() {
        return VIDEO_NOT_FOUND_EXCEPTION;
    }
}
