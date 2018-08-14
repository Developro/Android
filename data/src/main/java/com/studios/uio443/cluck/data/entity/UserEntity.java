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
import com.studios.uio443.cluck.data.util.JWTUtils;

import java.util.ArrayList;
import java.util.List;

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

    @SerializedName("points")
    private int points;

    @SerializedName("votes")
    private int votes;

    @SerializedName("nickName")
    private String nickName;

    @SerializedName("status")
    private Integer status;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private List<ResultEntity> resultEntity = null;

    @SerializedName("error")
    private Object error;

    public Integer getStatus() {
        return status;
  }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultEntity> getResultEntity() {
        return resultEntity;
    }

    public void setResultEntity(List<ResultEntity> result) {
        this.resultEntity = result;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public int getUserId() {
        if (userId == 0 || getAccessToken() != null) {
            try {
                userId = JWTUtils.getIntParamFromJWTBody(getAccessToken(), "id");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        if (this.resultEntity == null || this.resultEntity.size() == 0) {
            return null;
        } else {
            return this.resultEntity.get(0).getAccessToken();
        }
    }

    public void setAccessToken(String accessToken) {

        if (this.resultEntity == null) {
            this.resultEntity = new ArrayList<>();
        }
        if (this.resultEntity.size() == 0) {
            resultEntity.add(new ResultEntity(accessToken, null));
        } else {
            resultEntity.get(0).setAccessToken(accessToken);
        }
    }

    public String getRefreshToken() {
        if (this.resultEntity == null || this.resultEntity.size() == 0) {
            return null;
        } else {
            return this.resultEntity.get(0).getRefreshToken();
        }
    }

    public void setRefreshToken(String refreshToken) {
        if (this.resultEntity == null) {
            this.resultEntity = new ArrayList<>();
        }
        if (this.resultEntity.size() == 0) {
            resultEntity.add(new ResultEntity(null, refreshToken));
        } else {
            resultEntity.get(0).setRefreshToken(refreshToken);
        }
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
