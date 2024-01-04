package com.becourse.domain.video.domain.repository;

import com.becourse.domain.video.domain.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
}
