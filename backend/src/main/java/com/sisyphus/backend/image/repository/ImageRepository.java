package com.sisyphus.backend.image.repository;

import com.sisyphus.backend.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    /**
     * DB에 저장된 모든 image url을 조회한다.
     * - url은 public URL이므로 파일명 추출 후 orphan 정리에 사용한다.
     *
     * @return List<String> (역할: public URL 목록, 타입: List<String>)
     */
    @Query("select i.url from Image i")
    List<String> findAllUrls();
}
