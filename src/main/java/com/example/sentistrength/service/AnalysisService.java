package com.example.sentistrength.service;

import org.springframework.stereotype.Service;
import uk.ac.wlv.sentistrength.SentiStrength;

@Service
public interface AnalysisService {
    public void initialiseAndRun(String[] options);

    public String getUrl();
}
