package com.sisyphus.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "AccountRequest",
        description = "계정(Account) 관련 요청 DTO (추후 필드 추가 예정)"
)
public class AccountRequest {
    // TODO: provider, providerAccountId 등 필요 시 추가
}