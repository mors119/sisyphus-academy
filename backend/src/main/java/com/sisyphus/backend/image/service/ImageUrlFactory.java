package com.sisyphus.backend.image.service;

import com.sisyphus.backend.global.props.AppProps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageUrlFactory {

    private final AppProps appProps;

    /**
     * @param relativePath String (역할: 저장된 파일명, 타입: String) 예: "uuid.webp"
     * @return String (역할: 공개 URL, 타입: String) 예: "/uploads/images/year/month/uuid.webp" 또는 "https://img.example.com/uploads/images/uuid.webp"
     */
    public String publicUrl(String relativePath) {
        String base = appProps.image().publicBase(); // "/uploads/images/2025/12/uuid.webp"
        if (base == null || base.isBlank()) {
            throw new IllegalStateException("app.image.public-base 설정이 필요합니다.");
        }
        if (base.endsWith("/")) base = base.substring(0, base.length() - 1);
        return base + "/" + relativePath;
    }
}
