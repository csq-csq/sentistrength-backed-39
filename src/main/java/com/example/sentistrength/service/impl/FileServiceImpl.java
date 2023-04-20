package com.example.sentistrength.service.impl;

import com.example.sentistrength.enums.SentiStrengthManager;
import org.springframework.stereotype.Service;
import uk.ac.wlv.sentistrength.SentiStrength;
import uk.ac.wlv.sentistrength.classification.ClassificationResources;

@Service
public class FileServiceImpl {

    public void changeDictionaryFolder(String folderPath){
        SentiStrength sentiStrength = SentiStrengthManager.getInstance();
        ClassificationResources resources = sentiStrength.getCorpus().resources;
        resources.setNewDictionaryFolder(folderPath);
    }



}
