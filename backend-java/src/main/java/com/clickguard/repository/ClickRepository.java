package com.clickguard.repository;

import com.clickguard.model.Click;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface ClickRepository extends JpaRepository<Click, Long> {
    
    List <Click> findTop100ByOrderByTimestampDesc();
}