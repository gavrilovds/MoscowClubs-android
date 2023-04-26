package com.example.samsungproject2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.stream.Collectors;

public class Club {

    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("comments")
    private List<Comment> comments;
    @SerializedName("images")
    private List<ClubImages> images;
    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;
    @SerializedName("address")
    private String address;
    @SerializedName("closestUnderground")
    private String closestUnderground;
    @SerializedName("workTime")
    private String workTime;
    @SerializedName("peopleAmount")
    private Long peopleAmount;
    @SerializedName("meanPrice")
    private Long meanPrice;
    @SerializedName("webSite")
    private String webSite;
    @SerializedName("phoneNumber")
    private String phoneNumber;

    public Club() {
    }


    public List<ClubImages> getImages() {
        return images;
    }

    public void setImages(List<ClubImages> images) {
        this.images = images;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClosestUnderground() {
        return closestUnderground;
    }

    public void setClosestUnderground(String closestUnderground) {
        this.closestUnderground = closestUnderground;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public Long getPeopleAmount() {
        return peopleAmount;
    }

    public void setPeopleAmount(Long peopleAmount) {
        this.peopleAmount = peopleAmount;
    }

    public Long getMeanPrice() {
        return meanPrice;
    }

    public void setMeanPrice(Long meanPrice) {
        this.meanPrice = meanPrice;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
