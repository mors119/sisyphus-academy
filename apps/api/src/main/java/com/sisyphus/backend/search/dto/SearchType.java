package com.sisyphus.backend.search.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "SearchType",
        description = "통합 검색 결과 타입"
)
public enum SearchType {
    TAG,
    CATEGORY,
    NOTE
}