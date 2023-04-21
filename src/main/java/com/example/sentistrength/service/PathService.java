package com.example.sentistrength.service;

public interface PathService {
    public String getUploadPath();
    public String getDownloadPath();
    public String getResultPath();
    public void deleteFiles(String path);
}
