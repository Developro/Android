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
package com.studios.uio443.cluck.presentation.presenter;

import android.support.annotation.NonNull;

import com.studios.uio443.cluck.domain.User;
import com.studios.uio443.cluck.domain.exception.DefaultErrorBundle;
import com.studios.uio443.cluck.domain.exception.ErrorBundle;
import com.studios.uio443.cluck.domain.interactor.DefaultObserver;
import com.studios.uio443.cluck.domain.interactor.GetUserProfile;
import com.studios.uio443.cluck.domain.interactor.GetUserProfile.Params;
import com.studios.uio443.cluck.presentation.exception.ErrorMessageFactory;
import com.studios.uio443.cluck.presentation.internal.di.Scope.ActivityScope;
import com.studios.uio443.cluck.presentation.mapper.UserModelDataMapper;
import com.studios.uio443.cluck.presentation.model.UserModel;
import com.studios.uio443.cluck.presentation.view.UserProfileView;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@ActivityScope
public class UserProfilePresenter implements Presenter {

  private UserProfileView viewProfileView;

  private final GetUserProfile getUserProfileUseCase;
  private final UserModelDataMapper userModelDataMapper;

  @Inject
  public UserProfilePresenter(GetUserProfile getUserProfileUseCase,
                              UserModelDataMapper userModelDataMapper) {
    this.getUserProfileUseCase = getUserProfileUseCase;
    this.userModelDataMapper = userModelDataMapper;
  }

  public void setView(@NonNull UserProfileView view) {
    this.viewProfileView = view;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  @Override public void destroy() {
    this.getUserProfileUseCase.dispose();
    this.viewProfileView = null;
  }

  /**
   * Initializes the presenter by showing/hiding proper views
   * and retrieving user details.
   */
  public void initialize() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getUserDetails();
  }

  private void getUserDetails() {
    this.getUserProfileUseCase.execute(new UserProfileObserver(), Params.forCurrentUser());
  }

  private void showViewLoading() {
    this.viewProfileView.showLoading();
  }

  private void hideViewLoading() {
    this.viewProfileView.hideLoading();
  }

  private void showViewRetry() {
    this.viewProfileView.showRetry();
  }

  private void hideViewRetry() {
    this.viewProfileView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewProfileView.context(),
        errorBundle.getException());
    this.viewProfileView.showError(errorMessage);
  }

  private void showUserDetailsInView(User user) {
    final UserModel userModel = this.userModelDataMapper.transform(user);
    this.viewProfileView.renderUser(userModel);
  }

  private final class UserProfileObserver extends DefaultObserver<User> {

    @Override public void onComplete() {
      UserProfilePresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
      UserProfilePresenter.this.hideViewLoading();
      UserProfilePresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      UserProfilePresenter.this.showViewRetry();
    }

    @Override public void onNext(User user) {
      UserProfilePresenter.this.showUserDetailsInView(user);
    }
  }
}
