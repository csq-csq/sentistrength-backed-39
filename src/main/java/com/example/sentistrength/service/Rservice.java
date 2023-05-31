package com.example.sentistrength.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public interface Rservice {
    public void runRScript() throws IOException;
    public File exportResource(String resourceName) throws IOException;
}
