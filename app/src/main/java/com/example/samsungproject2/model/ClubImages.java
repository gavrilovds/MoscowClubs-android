package com.example.samsungproject2.model;

import com.google.gson.annotations.SerializedName;

public class ClubImages {

    @SerializedName("url")
    private String url;

    public ClubImages() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
