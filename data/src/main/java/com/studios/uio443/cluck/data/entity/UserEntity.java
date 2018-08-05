/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.studios.uio443.cluck.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * User Entity used in the data layer.
 */
public class UserEntity {

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

  @SerializedName("accessToken")
  private String accessToken;

  @SerializedName("refreshToken")
  private String refreshToken;

  @SerializedName("points")
  private int points;

  @SerializedName("votes")
  private int votes;

  @SerializedName("nickName")
  private String nickName;

  public UserEntity() {
    //empty
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

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getEmail() {
    return email;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
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
}
