package com.studios.uio443.cluck.presentation.presenter;

import android.content.Context;

import com.studios.uio443.cluck.domain.interactor.GetUserProfile;
import com.studios.uio443.cluck.domain.interactor.GetUserProfile.Params;
import com.studios.uio443.cluck.presentation.mapper.UserModelDataMapper;
import com.studios.uio443.cluck.presentation.util.LoginValidator;
import com.studios.uio443.cluck.presentation.view.fragment.LoginFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.DisposableObserver;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

        LoginValidator loginValidator = new LoginValidator(mockLoginFragment.context(), "user", "password");

        assertTrue(loginValidator.validate());

        verify(mockGetUserDetails).execute(any(DisposableObserver.class), any(Params.class));
    }
}