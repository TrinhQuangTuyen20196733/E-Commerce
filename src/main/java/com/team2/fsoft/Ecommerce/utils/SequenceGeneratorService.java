package com.team2.fsoft.Ecommerce.utils;

import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {

    private long currentValue = 1;

    public synchronized long getNextValue() {
        return currentValue++;
    }
}