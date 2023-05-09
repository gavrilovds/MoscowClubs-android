package com.example.samsungproject2.model.user;

import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.model.Comment;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.stream.Collectors;

public class User {
    private String email;
    private String name;
    private String token;
    private List<Comment> comments;
    private List<Club> favouriteClubs;
    @SerializedName("admin")
    private boolean isAdmin;
    private String adminClubName;
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Club> getFavouriteClubs() {
        return favouriteClubs;
    }

    public void setFavouriteClubs(List<Club> favouriteClubs) {
        this.favouriteClubs = favouriteClubs;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getAdminClubName() {
        return adminClubName;
    }

    public void setAdminClubName(String adminClubName) {
        this.adminClubName = adminClubName;
    }
}
