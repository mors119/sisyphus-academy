package com.sisyphus.backend.image.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileStorageService {
    /**
     * 파일 업로드 (멀티파트 기반)
     */
    String save(MultipartFile file);
    /**
     * 파일 업로드 (스트림 기반)
     * - 더미 시딩, 테스트, 외부 리소스 업로드 등에서 사용
     */
    String save(InputStream input, long size, String contentType, String originalFilename);
    /**
     * 파일 삭제
     */
    void delete(String publicUrlOrFilename);
}