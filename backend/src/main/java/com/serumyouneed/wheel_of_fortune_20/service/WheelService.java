package com.serumyouneed.wheel_of_fortune_20.service;

import java.util.Random;

import com.serumyouneed.wheel_of_fortune_20.utils.Sleeper;
import org.springframework.stereotype.Service;

/**
 * This class simulates spinning wheel.
 */
@Service
public class WheelService {

    private final Sleeper sleeper;

    public WheelService(Sleeper sleeper) {
        this.sleeper = sleeper;
    }

    /**
     * Function simulates fields of wheel with prizes for unwield letters as multiplier.
     * @param random      (int): Generate number simulating spin of the wheel.
     * @return multiplier (int): How many times will be multiplied each uncovered letter
     */
    public int switchToField(int random) {
        return switch (random) {
            case 1 -> 10;
            case 2 -> 20;
            case 3 -> 30;
            case 4 -> 40;
            case 5 -> 50;
            case 6 -> 100;
            default -> 1;
        };
    }

    /**
     * Function generates random number between 1-7 (included)
     * @return field (int): Selected number
     */
    public int spinTheWheel() {
        Random random = new Random();
        int field;
        field = random.nextInt(1, 7);
        return  field;
    }
}

