package com.api.socius.dtos;

import jakarta.validation.constraints.*;

import java.util.*;

public class PostDto {
    private String content;

    @NotNull
    private Long userId;

    private List<String> mediaUrls;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(List<String> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }
}
