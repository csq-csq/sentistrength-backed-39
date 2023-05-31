package com.example.sentistrength.entity;

import java.util.List;

public class DateListRequest {
    private List<List<String>> dateList;

    public List<List<String>> getDateList() {
        return dateList;
    }

    public void setDateList(List<List<String>> dateList) {
        this.dateList = dateList;
    }
}
