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

    @SerializedName("status")
    private Integer status;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private List<ResultEntity> resultEntity = null;

    @SerializedName("error")
    private Object error;

    private int userId;
    private int expDate;

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
        if (this.resultEntity != null && this.resultEntity.size() != 0) {
            userId = this.resultEntity.get(0).getUserId();
        }
        if (userId == 0 && getAccessToken() != null && !getAccessToken().isEmpty()) {
            try {
                userId = JWTUtils.getIntParamFromJWTBody(getAccessToken(), "id");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userId;
    }

    public int getExpDate() {
        if (expDate == 0 && getAccessToken() != null && !getAccessToken().isEmpty()) {
            try {
                expDate = JWTUtils.getIntParamFromJWTBody(getAccessToken(), "exp");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return expDate;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCoverUrl() {
        String coverUrl = "";
        if (resultEntity != null && resultEntity.size() != 0) {
            coverUrl = resultEntity.get(0).getCoverUrl();
        }
        return coverUrl;
    }

    public String getFullname() {
        String fullname = "";
        if (resultEntity != null && resultEntity.size() != 0) {
            fullname = resultEntity.get(0).getFullname();
        }
        return fullname;
    }

    public String getEmail() {
        String email = "";
        if (resultEntity != null && resultEntity.size() != 0) {
            email = resultEntity.get(0).getEmail();
        }
        return email;
    }

    public String getLogin() {
        String login = "";
        if (resultEntity != null && resultEntity.size() != 0) {
            login = resultEntity.get(0).getLogin();
        }
        return login;
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
        int points = 0;
        if (resultEntity != null && resultEntity.size() != 0) {
            points = resultEntity.get(0).getPoints();
        }
        return points;
    }

    public int getVotes() {
        int votes = 0;
        if (resultEntity != null && resultEntity.size() != 0) {
            votes = resultEntity.get(0).getVotes();
        }
        return votes;
    }

    public String getNickName() {
        String nickName = "";
        if (resultEntity != null && resultEntity.size() != 0) {
            nickName = resultEntity.get(0).getNickName();
        }
        return nickName;
    }
}
