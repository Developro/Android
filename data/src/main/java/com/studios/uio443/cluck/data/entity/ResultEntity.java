package com.studios.uio443.cluck.data.entity;

import com.google.gson.annotations.SerializedName;

class ResultEntity {

    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("refreshToken")
    private String refreshToken;

    @SerializedName("id")
    private int userId;

    @SerializedName("cover_url")
    private String coverUrl;

    @SerializedName("full_name")
    private String fullname;

    @SerializedName("email")
    private String email;

    @SerializedName("login")
    private String login;

    @SerializedName("points")
    private int points;

    @SerializedName("votes")
    private int votes;

    @SerializedName("nickName")
    private String nickName;

    @SerializedName("status")
    private String status;

    ResultEntity(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
