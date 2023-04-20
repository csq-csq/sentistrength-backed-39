package com.example.sentistrength.enums;

import uk.ac.wlv.sentistrength.SentiStrength;

public class SentiStrengthManager {

    private static final SentiStrength instance = new SentiStrength();

    public static SentiStrength getInstance() {
        return instance;
    }


}
