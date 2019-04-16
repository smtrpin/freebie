package com.youhack.freebie.dto;

public class DrawDto {
    private Long id;
    private String url;
    private String caption;
    private String author;
    private Boolean isCompleted;

    public DrawDto(Long id, String url, String caption, String author, Boolean isCompleted) {
        this.id = id;
        this.url = url;
        this.caption = caption;
        this.author = author;
        this.isCompleted = isCompleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
