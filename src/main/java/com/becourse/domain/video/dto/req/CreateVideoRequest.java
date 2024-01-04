package com.becourse.domain.video.dto.req;

import java.util.List;

public record CreateVideoRequest(String title, String content, List<String> tag, String creator,int price) {}

/*
    제목
    내용
    태그 (서버,웹 등등)
    만든날짜
    만든이
    가격
 */