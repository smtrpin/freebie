package com.youhack.freebie.repository;

import com.youhack.freebie.entity.Freebie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FreebieRepository extends JpaRepository<Freebie, Long> {
    Freebie findByThreadId(Long threadId);
}
