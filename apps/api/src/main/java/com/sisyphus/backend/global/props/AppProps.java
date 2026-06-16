package com.sisyphus.backend.global.props;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@ConfigurationProperties(prefix = "app")
public record AppProps(
        Hosts hosts,
        Image image,
        Cors cors,
        Upload upload,
        Cookie cookie
) {
    public record Hosts(
            @NotBlank String app,              // 역할: Web App base URL (type: String) 예: https://app.example.com
            @NotBlank String api,              // 역할: API base URL (type: String) 예: https://api.example.com
            @NotBlank String img,              // 역할: 이미지 CDN/서빙 URL (type: String)
            @NotBlank String chromeExtension   // 역할: 크롬 익스텐션 origin (type: String) 예: chrome-extension://<EXTENSION_ID>
    ) {}

    public record Image(
            @NotBlank String publicBase        // 역할: 외부 공개 이미지 base (type: String)
    ) {}

    public record Cors(
            @NotEmpty List<@NotBlank String> allowedOrigins // 역할: 허용할 Origin 목록 (type: List<String>)
    ) {}

    public record Upload(
            @NotEmpty List<@Pattern(regexp = "^[a-z0-9]+$") String> allowedExtensions // 역할: 업로드 허용 확장자 (type: List<String>)
    ) {}

    public record Cookie(
            String domain,                      // 역할: 쿠키 도메인 (type: String) - 로컬은 null/빈값 허용
            boolean secure,                     // 역할: Secure 쿠키 여부 (type: boolean) - 운영은 true 권장
            @Pattern(regexp = "^(Lax|None|Strict)$")
            String samesite                     // 역할: SameSite 정책 (type: String: Lax/None/Strict)
    ) {}
}
