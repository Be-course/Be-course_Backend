package com.becourse.domain.video.dto.res;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
public class VideoDto {
    private Long id;
    private String title;
    private String content;
    private List<String> tag ;
    private LocalDate createAt;
    private String creator;
    private int price;
    private String video_url;
}
