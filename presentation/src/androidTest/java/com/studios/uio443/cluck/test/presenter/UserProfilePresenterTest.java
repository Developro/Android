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
package com.studios.uio443.cluck.test.presenter;

import android.content.Context;

import com.studios.uio443.cluck.domain.interactor.GetUserDetails;
import com.studios.uio443.cluck.domain.interactor.GetUserDetails.Params;
import com.studios.uio443.cluck.presentation.mapper.UserModelDataMapper;
import com.studios.uio443.cluck.presentation.presenter.UserProfilePresenter;
import com.studios.uio443.cluck.presentation.view.UserProfileView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserProfilePresenterTest {

  private static final int USER_ID = 1;

    private UserProfilePresenter userProfilePresenter;

  @Mock private Context mockContext;
    @Mock
    private UserProfileView mockUserProfileView;
  @Mock private GetUserDetails mockGetUserDetails;
  @Mock private UserModelDataMapper mockUserModelDataMapper;

  @Before
  public void setUp() {
      userProfilePresenter = new UserProfilePresenter(mockGetUserDetails, mockUserModelDataMapper);
      userProfilePresenter.setView(mockUserProfileView);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testUserDetailsPresenterInitialize() {
      given(mockUserProfileView.context()).willReturn(mockContext);

      userProfilePresenter.initialize(USER_ID);

      verify(mockUserProfileView).hideRetry();
      verify(mockUserProfileView).showLoading();
    verify(mockGetUserDetails).execute(any(DisposableObserver.class), any(Params.class));
  }
}
