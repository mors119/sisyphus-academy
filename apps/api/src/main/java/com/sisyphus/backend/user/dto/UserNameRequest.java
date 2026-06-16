package com.sisyphus.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(
        name = "UserNameRequest",
        description = "유저 이름(프로필) 수정 요청 DTO"
)
public class UserNameRequest {

    @Schema(
            description = "변경할 사용자 이름",
            example = "Heeseong"
    )
    @NotBlank(message = "name은 비어 있을 수 없습니다.")
    private String name; // 필요한 경우에만 포함
}
