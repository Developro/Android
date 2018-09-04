/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.studios.uio443.cluck.data.repository;

import com.google.gson.JsonObject;
import com.studios.uio443.cluck.data.entity.mapper.UserEntityDataMapper;
import com.studios.uio443.cluck.data.repository.datasource.UserDataStore;
import com.studios.uio443.cluck.data.repository.datasource.UserDataStoreFactory;
import com.studios.uio443.cluck.domain.User;
import com.studios.uio443.cluck.domain.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * {@link UserRepository} for retrieving user data.
 */
@Singleton
public class UserDataRepository implements UserRepository {

  private final UserDataStoreFactory userDataStoreFactory;
  private final UserEntityDataMapper userEntityDataMapper;

  /**
   * Constructs a {@link UserRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param userEntityDataMapper {@link UserEntityDataMapper}.
   */
  @Inject
  UserDataRepository(UserDataStoreFactory dataStoreFactory,
      UserEntityDataMapper userEntityDataMapper) {
    this.userDataStoreFactory = dataStoreFactory;
    this.userEntityDataMapper = userEntityDataMapper;
  }

  @Override public Observable<List<User>> users() {
    //we always get all users from the cloud
    final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
    return userDataStore.userEntityList().map(this.userEntityDataMapper::transform);
  }

  @Override public Observable<User> user(int userId) {
    final UserDataStore userDataStore = this.userDataStoreFactory.create(userId);
    return userDataStore.userEntityDetails(userId).map(this.userEntityDataMapper::transform);
  }

  @Override
  public Observable<User> userProfile(int userId) {
    return this.userDataStoreFactory.getCluckyApiImpl().getUser(userId).map(this.userEntityDataMapper::transform);
  }

  @Override
  public Observable<User> currentUserProfile(int userId) {
    return this.userDataStoreFactory.getCluckyApiImpl().getCurrentUser(userId).map(this.userEntityDataMapper::transform);
  }

  @Override
  public Observable<User> auth(String user, String password) {
    // т.к. у нас максимально тонкий клиент, то юзать UserDataStore не будем
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("login", user);
    jsonObject.addProperty("password", password);
    return this.userDataStoreFactory.getCluckyApiImpl().auth(
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())).map(this.userEntityDataMapper::transform);
  }
}
