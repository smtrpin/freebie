package com.youhack.freebie.service;

import com.youhack.freebie.entity.Freebie;

public interface FreebieService {
    Freebie findFreebieById(Long id);
    void saveFreebie(Freebie freebie);
}
