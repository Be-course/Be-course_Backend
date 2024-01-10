package com.becourse.domain.video.exception.error;

import com.becourse.global.exception.error.ErrorProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum VideoError implements ErrorProperty {

    VIDEO_NOT_FOUND (HttpStatus.NOT_FOUND, "비디오를 찾을 수 업습니다."),
    VIDEO_NO_ACCESS_ERROR (HttpStatus.FORBIDDEN, "비디오에 대한 권한이 없습니다.")
    ;

    private final HttpStatus status;
    private final String message;

}
