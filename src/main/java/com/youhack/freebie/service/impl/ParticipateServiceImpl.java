package com.youhack.freebie.service.impl;

import com.youhack.freebie.config.YouHackConfig;
import com.youhack.freebie.dto.DrawDto;
import com.youhack.freebie.entity.Freebie;
import com.youhack.freebie.service.DriverService;
import com.youhack.freebie.service.FreebieService;
import com.youhack.freebie.service.ParticipateService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class ParticipateServiceImpl implements ParticipateService {

    @Autowired
    YouHackConfig youHackConfig;

    @Autowired
    private DriverService driverService;

    @Autowired
    private FreebieService freebieService;

    @Override
    public void participate() {
        WebDriver driver = getWebDriverWithCookie();
        driver.get(youHackConfig.getParticipateUrl());
        String pageSource = driver.getPageSource();
        if (isOk(pageSource)) {
            Integer countPages = getCountPages(pageSource);
            for (int i = 2; i <= countPages; i++) {
                if (isOk(pageSource)) {
                    Set<DrawDto> draws = getDraws(pageSource);
                    if (draws.size() > 0) {
                        participantDraw(driver, draws);
                    }
                }
                if (youHackConfig.getPageLimit() != 0 && i > youHackConfig.getPageLimit()) {
                    break;
                }
                driver.navigate().to(youHackConfig.getParticipateUrl() + "page-" + i);
                pageSource = driver.getPageSource();
            }
        }
        driver.quit();
    }

    private Set<DrawDto> getDraws(String pageSource) {
        Set<DrawDto> draws = new LinkedHashSet<>();
        Document doc = Jsoup.parse(pageSource);
        if (doc.select("ol.discussionListItems").size() > 0) {
            Element topics = doc.select("ol.discussionListItems").get(0);
            if (topics.select("li").size() > 0) {
                Elements themes = topics.select("li");
                themes.forEach(it -> draws.add(getDrawDto(it)));
            }
        }
        return draws;
    }

    private void participantDraw(WebDriver driver, Set<DrawDto> draws) {
        draws.forEach(it -> {
            Freebie freebie = freebieService.findFreebieById(it.getId());
            if (freebie == null || (!freebie.getParticipate() && !freebie.getCompleted()) && !it.getCompleted()) {
                driver.navigate().to(youHackConfig.getHost() + it.getUrl());
                participantDraw(driver, freebie, it);
            }
        });
    }

    private void participantDraw(WebDriver driver, Freebie freebie, DrawDto drawDto) {
        String pageSource = driver.getPageSource();
        Document document = Jsoup.parse(pageSource);
        if (isOk(pageSource)) {
            String text = "";
            if (document.select("blockquote.messageText").size() > 0) {
                text = document.select("blockquote.messageText").get(0).text().replaceAll("\\s?Информация .*", "");
            }
            freebie = freebie == null ? new Freebie() : freebie;
            freebie.setCaption(drawDto.getCaption());
            freebie.setThreadId(drawDto.getId());
            freebie.setFreebieText(text);
            freebie.setParticipate(false);
            if (pageSource.contains("Принять участие в розыгрыше")) {
                driver.findElement(By.cssSelector(".contestThreadBlock >a.button")).click();
                freebie.setParticipate(true);
            }
            freebie.setCompleted(drawDto.getCompleted());
            freebieService.saveFreebie(freebie);
        }
    }

    private DrawDto getDrawDto(Element element) {
        Long id = Long.valueOf(element.attr("id").split("-")[1]);
        String url = element.select("a.PreviewTooltip").get(0).attr("href");
        String caption = element.select("a.PreviewTooltip").get(0).text();
        String author = element.select("a.username > span").get(0).text();
        boolean isCompleted = true;
        if (element.select("div.pairsJustified dd").get(0).text().equals("0")) {
            isCompleted = false;
        }
        return new DrawDto(
                id,
                url,
                caption,
                author,
                isCompleted
        );
    }

    private Integer getCountPages(String pageSource) {
        Document doc = Jsoup.parse(pageSource);
        Integer pages = 0;
        if (doc.select("div.PageNav").size() > 0) {
            Element pageNav = doc.select("div.PageNav").get(0);
            if (pageNav.select("a").size() > 0) {
                Element lastPage = pageNav.select("a").get(pageNav.select("a").size() - 2);
                pages = Integer.valueOf(lastPage.text());
            }
        }
        return pages;
    }

    private boolean isOk(String pageSource) {
        return pageSource.contains(youHackConfig.getUsername());
    }

    private WebDriver getWebDriverWithCookie() {
        WebDriver webDriver = driverService.setHost(youHackConfig.getDomain())
                .setJavaScriptEnable(true)
                .setUserAgent(youHackConfig.getUserAgent())
                .build();
        Set<Cookie> cookies = getCookies();

        cookies.forEach(it -> {
            try {
                webDriver.manage().addCookie(it);
            } catch (Exception ignored) {

            }
        });
        return webDriver;
    }

    private Set<Cookie> getCookies() {
        Set<Cookie> cookies = new HashSet<>();
        cookies.add(getIdStackCookie());
        cookies.add(getXfUserCookie());
        cookies.add(getXfSession());
        return cookies;
    }

    private Cookie getIdStackCookie() {
        return new Cookie.Builder("IDstack", youHackConfig.getIdStack())
                .path("/")
                .domain(youHackConfig.getDomain())
                .expiresOn(new Date(2020, 12, 31))
                .isHttpOnly(false)
                .isSecure(false)
                .build();
    }

    private Cookie getXfUserCookie() {
        return new Cookie.Builder("xf_user", youHackConfig.getXfUser())
                .path("/")
                .domain(youHackConfig.getDomain())
                .expiresOn(new Date(2020, 12, 31))
                .isHttpOnly(true)
                .isSecure(true)
                .build();
    }

    private Cookie getXfSession() {
        return new Cookie.Builder("xf_session", youHackConfig.getXfSession())
                .path("/")
                .domain(youHackConfig.getDomain())
                .isHttpOnly(true)
                .isSecure(true)
                .build();
    }
}
