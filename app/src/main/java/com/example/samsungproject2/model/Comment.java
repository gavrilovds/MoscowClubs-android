package com.example.samsungproject2.model;

import com.example.samsungproject2.model.user.User;
import com.google.gson.annotations.SerializedName;

public class Comment {
    private String text;
    private String owner;
    private String clubName;
    private Long id;
    public Comment() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
