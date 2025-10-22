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
    public int switchToField(int random, long millis) {
        int multiplier = 0;
        switch (random) {
            case 1:
                sleeper.sleep(millis);
                multiplier = 10;
                break;
            case 2:
                sleeper.sleep(millis);
                multiplier = 20;
                break;
            case 3:
                sleeper.sleep(millis);
                multiplier = 30;
                break;
            case 4:
                sleeper.sleep(millis);
                multiplier = 50;
                break;
            case 5:
                sleeper.sleep(millis);
                multiplier = 100;
                break;
            default:
                multiplier = 1;
        }
        return multiplier;
    }

    /**
     * Function generates random number between 1-7 (included)
     * @return field (int): Selected number
     */
    public int spinTheWheel() {
        Random random = new Random();
        int field;
        field = random.nextInt(1, 6);
        return  field;
    }
}

