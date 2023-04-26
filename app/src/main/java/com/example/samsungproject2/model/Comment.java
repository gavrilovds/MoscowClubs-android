package com.example.samsungproject2.model;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("text")
    private String text;
    @SerializedName("owner")
    private String owner;


    public Comment() {
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
