package com.sisyphus.backend.global.config;

import com.sisyphus.backend.global.props.AppProps;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(AppProps appProps) {
        return new OpenAPI()
                .info(new Info()
                        .title("Sisyphus API")
                        .description("Sisyphus 백엔드 API 문서")
                        .version("v1")
                        .contact(new Contact().name("Sisyphus Backend")))
                .servers(List.of(
                        new Server().url(appProps.hosts().api()).description("Configured API")
                ));
    }
}

/**
 *
 *  아래와 동일 annotation 과 Bean 방식 차이
 *
 * @Configuration
 * public class OpenApiConfig {
 *
 *     @Bean
 *     public OpenAPI openAPI() {
 *         final String schemeName = "bearerAuth";
 *
 *         return new OpenAPI()
 *                 .info(new Info()
 *                         .title("Sisyphus API")
 *                         .version("v1")
 *                         .description("Sisyphus 백엔드 API 문서"))
 *                 .servers(List.of(
 *                         new Server().url("http://localhost:8080").description("Local")
 *                         // new Server().url("https://api.sisyphus.com").description("Production")
 *                 ))
 *                 .components(new Components()
 *                         .addSecuritySchemes(schemeName,
 *                                 new SecurityScheme()
 *                                         .type(SecurityScheme.Type.HTTP)
 *                                         .scheme("bearer")
 *                                         .bearerFormat("JWT")
 *                         )
 *                 )
 *                 // 전역으로 인증 요구를 걸고 싶으면 유지
 *                 .addSecurityItem(new SecurityRequirement().addList(schemeName));
 *     }
 * }
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * */
