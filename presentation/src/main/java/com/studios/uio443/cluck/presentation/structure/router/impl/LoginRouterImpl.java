package com.studios.uio443.cluck.presentation.structure.router.impl;

import com.studios.uio443.cluck.presentation.R;
import com.studios.uio443.cluck.presentation.structure.router.LoginRouter;
import com.studios.uio443.cluck.presentation.util.Consts;
import com.studios.uio443.cluck.presentation.view.activity.LoginActivity;
import com.studios.uio443.cluck.presentation.view.activity.LoginPinActivity;
import com.studios.uio443.cluck.presentation.view.activity.ModeSelectActivity;
import com.studios.uio443.cluck.presentation.view.fragment.LoginFragment;
import com.studios.uio443.cluck.presentation.view.fragment.LogoutFragment;
import com.studios.uio443.cluck.presentation.view.fragment.SignupFragment;

import javax.inject.Inject;

public class LoginRouterImpl extends BaseRouterImpl<LoginActivity> implements LoginRouter {

	@Inject
	public LoginRouterImpl(LoginActivity activity) {
		super(activity);
	}

	@Override
	public void showLoginFragment() {
		replaceFragment(R.id.container, new LoginFragment());
	}

	@Override
	public void showLogoutFragment() {
		replaceFragment(R.id.container, new LogoutFragment());
	}

	@Override
	public void showSignupFragment() {
		addFragment(R.id.container, new SignupFragment());
	}

	@Override
	public void showLoginActivity() {
		startActivity(LoginActivity.class);
	}

	@Override
	public void showLoginPinActivity() {
		startActivityForResult(LoginPinActivity.class, Consts.REQUEST_CODE_LOGIN_PIN_ACTIVITY);
	}

	@Override
	public void showModeSelectActivity() {
		startActivity(ModeSelectActivity.class);
	}
}
