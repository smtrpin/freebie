package com.youhack.freebie.service.impl;

import com.youhack.freebie.entity.Freebie;
import com.youhack.freebie.repository.FreebieRepository;
import com.youhack.freebie.service.FreebieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreebieServiceImpl implements FreebieService {

    @Autowired
    FreebieRepository freebieRepository;

    @Override
    public Freebie findFreebieById(Long id) {
        return freebieRepository.findByThreadId(id);
    }

    @Override
    public void saveFreebie(Freebie freebie) {
        freebieRepository.saveAndFlush(freebie);
    }
}
