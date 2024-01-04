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

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String tag ;

    @Column
    private LocalDate createAt;

    @Column
    private String creator;

    @Column
    private int price;

    @Column
    private String video_url;

    @Builder
    public VideoEntity(String title, String content, String tag, LocalDate createAt, String creator, int price, String video_url) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.createAt = createAt;
        this.creator = creator;
        this.price = price;
        this.video_url = video_url;
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