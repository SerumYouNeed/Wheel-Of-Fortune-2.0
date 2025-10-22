package com.serumyouneed.wheel_of_fortune_20.utils;

import org.springframework.stereotype.Component;

@Component
public class RealSleeper implements Sleeper {

    /**
     * Function stops program for two seconds.
     */
    @Override
    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

