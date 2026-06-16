package com.sisyphus.backend.search.controller;

import com.sisyphus.backend.search.dto.SearchResponse;
import com.sisyphus.backend.search.service.SearchService;
import com.sisyphus.backend.security.principal.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.sisyphus.backend.global.error.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;


@Tag(name = "Search", description = "통합 검색 API (tag/category/note)")
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @Operation(
            summary = "통합 검색",
            description = """
                tag/category/note를 한 번에 검색합니다.
                q(검색어)는 필수이며, Pageable로 결과를 제한합니다.
                """
    )
    @ApiResponse(
            responseCode = "400",
            description = "요청 값 오류",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "인증 실패",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "서버 오류",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<List<SearchResponse>> search(
            @Parameter(description = "검색어", example = "oauth")
            @RequestParam("q") @NotBlank String keyword,

            @Parameter(description = "페이징/정렬 파라미터. page=0부터 시작, size=페이지 크기, sort=필드,방향 (예: createdAt,desc)")
            @PageableDefault(size = 5) Pageable pageable,

            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal principal
    ) {
        Long userId = principal.getId();

        return ResponseEntity.ok(searchService.search(keyword, userId, pageable));
    }
}
