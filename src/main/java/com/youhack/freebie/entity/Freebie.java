package com.youhack.freebie.entity;

import javax.persistence.*;

@Entity
public class Freebie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "caption")
    private String caption;

    @Column(name = "freebie_text", nullable = false)
    private String freebieText;

    @Column(name = "thread_id", nullable = false)
    private Long threadId;

    @Column(name = "is_participate", nullable = false)
    private Boolean isParticipate;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getFreebieText() {
        return freebieText;
    }

    public void setFreebieText(String freebieText) {
        this.freebieText = freebieText;
    }

    public Long getThreadId() {
        return threadId;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }

    public Boolean getParticipate() {
        return isParticipate;
    }

    public void setParticipate(Boolean participate) {
        isParticipate = participate;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
