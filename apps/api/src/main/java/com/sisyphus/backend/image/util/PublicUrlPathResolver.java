package com.sisyphus.backend.image.util;

import java.net.URI;

public final class PublicUrlPathResolver {

    private PublicUrlPathResolver() {}

    /**
     * public-base를 URL/경로 어떤 형태로 설정했든 비교 가능한 "path"로 정규화한다.
     *
     * @param publicBase String (역할: app.image.public-base 원본, 타입: String)
     * @return String (역할: path 형태 public base, 타입: String) 예: "/uploads/images"
     */
    public static String normalizePublicBasePath(String publicBase) {
        if (publicBase == null || publicBase.isBlank()) {
            throw new IllegalStateException("app.image.public-base 설정이 필요합니다.");
        }

        try {
            // "https://img.example.com/uploads/images" -> "/uploads/images"
            String path = URI.create(publicBase).getPath();
            if (path == null || path.isBlank()) throw new IllegalArgumentException();
            if (path.endsWith("/")) path = path.substring(0, path.length() - 1);
            return path;
        } catch (Exception ignore) {
            // "/uploads/images"
            String path = publicBase;
            if (!path.startsWith("/")) path = "/" + path;
            if (path.endsWith("/")) path = path.substring(0, path.length() - 1);
            return path;
        }
    }

    /**
     * DB에 저장된 public URL에서 상대경로를 추출한다.
     *
     * 예)
     * - publicBasePath = "/uploads/images"
     * - publicUrl = "https://img.example.com/uploads/images/2025/12/uuid.webp"
     *   -> "2025/12/uuid.webp"
     *
     * @param publicUrl String (역할: DB의 Image.url, 타입: String)
     * @param publicBasePath String (역할: "/uploads/images" 같은 base path, 타입: String)
     * @return String (역할: "2025/12/uuid.webp" 같은 상대경로, 타입: String)
     */
    public static String extractRelativePathFromPublicUrl(String publicUrl, String publicBasePath) {
        if (publicUrl == null || publicUrl.isBlank()) return null;
        if (publicBasePath == null || publicBasePath.isBlank()) return null;

        String path;
        try {
            path = URI.create(publicUrl).getPath(); // 절대 URL/상대 URL 모두 대응
        } catch (Exception e) {
            path = publicUrl; // 파싱 실패하면 문자열로 처리
        }

        if (path == null || path.isBlank()) return null;

        // "/uploads/images/" 이후만 잘라낸다
        int idx = path.indexOf(publicBasePath + "/");
        if (idx < 0) return null;

        return path.substring(idx + (publicBasePath + "/").length());
    }
}
