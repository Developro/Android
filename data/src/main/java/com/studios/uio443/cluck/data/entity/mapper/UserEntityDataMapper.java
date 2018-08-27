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
package com.studios.uio443.cluck.data.entity.mapper;

import com.studios.uio443.cluck.data.entity.UserEntity;
import com.studios.uio443.cluck.data.retrofit.GetUser;
import com.studios.uio443.cluck.data.util.SharedPreferencesUtil;
import com.studios.uio443.cluck.domain.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.studios.uio443.cluck.data.util.Consts.ACCESS_TOKEN_TAG;
import static com.studios.uio443.cluck.data.util.Consts.EXP_DATE_TAG;
import static com.studios.uio443.cluck.data.util.Consts.REFRESH_TOKEN_TAG;

/**
 * Mapper class used to transform {@link UserEntity} (in the data layer) to {@link User} in the
 * domain layer.
 */
@Singleton
public class UserEntityDataMapper {

  @Inject
  UserEntityDataMapper() {}

  /**
   * Transform a {@link UserEntity} into an {@link User}.
   *
   * @param userEntity Object to be transformed.
   * @return {@link User} if valid {@link UserEntity} otherwise null.
   */
  public User transform(UserEntity userEntity) {
    User user = null;
    if (userEntity != null) {
      user = new User(userEntity.getUserId());
      user.setCoverUrl(userEntity.getCoverUrl());
      user.setFullName(userEntity.getFullname());
      user.setEmail(userEntity.getEmail());
      user.setAccessToken(userEntity.getAccessToken());
      user.setLogin(userEntity.getLogin());
      user.setNickName(userEntity.getNickName());
      user.setRefreshToken(userEntity.getRefreshToken());
      user.setPoints(userEntity.getPoints());
      user.setVotes(userEntity.getVotes());
      user.setExpDate(userEntity.getExpDate());

      // пока точно не знаю куда воткнуть, самый простой способ это сюда
      if (GetUser.getInstance().tokenIsEmpty() && userEntity.getAccessToken() != null) {
        GetUser.getInstance().setToken(userEntity.getAccessToken());
        SharedPreferencesUtil.savePropertyWithEncrypt(ACCESS_TOKEN_TAG, userEntity.getAccessToken());
      }
      // тут также сохранить нужные нам свойства, будем с ними работать
      if (userEntity.getExpDate() != 0) {
        int expiresIn = userEntity.getExpDate();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, expiresIn);
        SharedPreferencesUtil.saveProperty(EXP_DATE_TAG, c.getTimeInMillis());
      }

      if (userEntity.getRefreshToken() != null)
        SharedPreferencesUtil.savePropertyWithEncrypt(REFRESH_TOKEN_TAG, userEntity.getRefreshToken());
    }
    return user;
  }

  /**
   * Transform a List of {@link UserEntity} into a Collection of {@link User}.
   *
   * @param userEntityCollection Object Collection to be transformed.
   * @return {@link User} if valid {@link UserEntity} otherwise null.
   */
  public List<User> transform(Collection<UserEntity> userEntityCollection) {
    final List<User> userList = new ArrayList<>(20);
    for (UserEntity userEntity : userEntityCollection) {
      final User user = transform(userEntity);
      if (user != null) {
        userList.add(user);
      }
    }
    return userList;
  }
}
