package com.youhack.freebie.sheduler;

import com.youhack.freebie.service.ParticipateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FreebieScheduled {

    @Autowired
    ParticipateService participateService;

    @Scheduled(cron = "0/30 * * * * ?")
    public void participate() {
        String uuid = UUID.randomUUID().toString();
        participateService.participate();
        System.out.println(uuid + " закончил работу!");
    }
}
