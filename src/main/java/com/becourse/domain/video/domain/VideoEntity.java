package com.becourse.domain.video.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String tag ;

    @Column(nullable = false)
    private LocalDate createAt;

    @Column(nullable = false)
    private String creator;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String video_url;

    @Column(nullable = false)
    private Long check_info; // 비디오 작성자 pk 저장용

    @Builder
    public VideoEntity(String title, String content, String tag, LocalDate createAt, String creator, int price, String video_url , Long check_info) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.createAt = createAt;
        this.creator = creator;
        this.price = price;
        this.video_url = video_url;
        this.check_info = check_info;
    }
}

/*
    순서대로
    제목
    내용
    태그 (서버,웹 등등)
    만든날짜
    만든이
    가격
    영상링크
 */