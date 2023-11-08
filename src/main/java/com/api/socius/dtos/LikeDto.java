package com.api.socius.dtos;

import jakarta.validation.constraints.NotNull;

public class LikeDto {
    @NotNull
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
