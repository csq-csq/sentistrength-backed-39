package com.example.sentistrength.service;

import uk.ac.wlv.sentistrength.SentiStrength;

public class TestService {

    public static void main(String[] args){
        SentiStrength sentiStrength = new SentiStrength();
        sentiStrength.initialiseAndRun(new String[0]);
    }

}
