package com.studios.uio443.cluck.presentation.presenter;

import android.content.Context;

import com.studios.uio443.cluck.domain.interactor.GetUserProfile;
import com.studios.uio443.cluck.presentation.mapper.UserModelDataMapper;
import com.studios.uio443.cluck.presentation.view.fragment.LoginFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginFragmentPresenterTest {

    private LoginFragmentPresenter loginFragmentPresenter;

    @Mock
    private Context mockContext;
    @Mock
    private GetUserProfile mockGetUserDetails;
    @Mock
    private UserModelDataMapper mockUserModelDataMapper;
    @Mock
    private LoginFragment mockLoginFragment;


    @Before
    public void setUp() {
        loginFragmentPresenter = new LoginFragmentPresenter(mockGetUserDetails, mockUserModelDataMapper);
        loginFragmentPresenter.bindView(mockLoginFragment);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void onSignInVK() {
        verify(mockLoginFragment).VKSdkLogin();
    }

    @Test
    public void onLogin() {
        given(mockLoginFragment.context()).willReturn(mockContext);

        loginFragmentPresenter.onLogin("user", "password");
    }
}