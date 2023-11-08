package com.api.socius.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_POSTS")
public class PostModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS", timezone = "UTC")
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS", timezone = "UTC")
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "userId")
    @NotNull
    private UserModel user;

    @ManyToMany
    @JoinTable(
            name = "postLikes",
            joinColumns = @JoinColumn(name = "postId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private List<UserModel> likes;

    @ElementCollection
    @CollectionTable(name = "postMedia", joinColumns = @JoinColumn(name = "postId"))
    @Column(name = "mediaUrl")
    private List<String> mediaUrls;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<UserModel> getLikes() {
        return likes;
    }

    public void setLikes(List<UserModel> likes) {
        this.likes = likes;
    }

    public List<String> getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(List<String> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }
}
