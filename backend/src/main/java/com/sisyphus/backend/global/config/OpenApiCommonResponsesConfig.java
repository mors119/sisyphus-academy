package com.sisyphus.backend.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Swagger의 모든 endpoint에 대해:
//      400/401/403/500 응답이 자동으로 보이게 됨
@Configuration
public class OpenApiCommonResponsesConfig {

    @Bean
    public OpenApiCustomizer globalErrorResponsesCustomizer() {
        return (OpenAPI openApi) -> {
            // components/schemas에 ErrorResponse 스키마를 등록
            // (springdoc이 자동으로 등록할 때도 많지만, 확실히 하기 위해 명시)
            if (openApi.getComponents() == null) {
                openApi.setComponents(new io.swagger.v3.oas.models.Components());
            }
            if (openApi.getComponents().getSchemas() == null) {
                openApi.getComponents().setSchemas(new java.util.HashMap<>());
            }
            openApi.getComponents().getSchemas()
                    .putIfAbsent("ErrorResponse", new Schema<>().$ref("#/components/schemas/ErrorResponse"));

            // 모든 operation에 공통 응답을 주입
            openApi.getPaths().forEach((path, pathItem) -> pathItem.readOperations().forEach(operation -> {
                ApiResponses responses = operation.getResponses();
                if (responses == null) {
                    responses = new ApiResponses();
                    operation.setResponses(responses);
                }

                // 400/401/403/500을 전역 기본값으로 추가
                addErrorResponseIfAbsent(responses, "400", "요청 값 오류");
                addErrorResponseIfAbsent(responses, "401", "인증 실패");
                addErrorResponseIfAbsent(responses, "403", "권한 없음");
                addErrorResponseIfAbsent(responses, "500", "서버 오류");
            }));
        };
    }

    private void addErrorResponseIfAbsent(ApiResponses responses, String status, String description) {
        if (responses.containsKey(status)) return;

        responses.addApiResponse(status, new ApiResponse()
                .description(description)
                .content(new Content().addMediaType(
                        org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/ErrorResponse"))
                )));
    }
}
