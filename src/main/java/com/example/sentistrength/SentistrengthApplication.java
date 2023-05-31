package com.example.sentistrength;

import com.example.sentistrength.service.PathService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.ac.wlv.sentistrength.SentiStrength;

@SpringBootApplication
public class SentistrengthApplication {
    public static void main(String[] args) {

        SpringApplication.run(SentistrengthApplication.class, args);
        SentiStrength.main("["","","","",""]");
    }

}
