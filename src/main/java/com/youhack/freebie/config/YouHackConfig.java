package com.youhack.freebie.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class YouHackConfig {

    @Value("${youhack.participate.url}")
    private String participateUrl;

    @Value("${youhack.site.domain}")
    private String domain;

    @Value("${youhack.site.host}")
    private String host;

    @Value("${youhack.participate.userAgent}")
    private String userAgent;

    @Value("${youhack.account.username}")
    private String username;

    @Value("${youhack.account.cookie.IDstack}")
    private String idStack;

    @Value("${youhack.account.cookie.xf_user}")
    private String xfUser;

    @Value("${youhack.account.cookie.xf_session}")
    private String xfSession;

    @Value("${youhack.pages.limit}")
    private Integer pageLimit;

    public String getParticipateUrl() {
        return participateUrl;
    }

    public String getDomain() {
        return domain;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getUsername() {
        return username;
    }

    public String getIdStack() {
        return idStack;
    }

    public String getXfUser() {
        return xfUser;
    }

    public String getXfSession() {
        return xfSession;
    }

    public Integer getPageLimit() {
        return pageLimit;
    }

    public String getHost() {
        return host;
    }
}
