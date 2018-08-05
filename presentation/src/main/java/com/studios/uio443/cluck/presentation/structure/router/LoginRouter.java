package com.studios.uio443.cluck.presentation.structure.router;

import com.studios.uio443.cluck.presentation.mvp.FragmentNavigation;

public interface LoginRouter {
	void showLoginFragment();

	void showLogoutFragment();

	void showSignupFragment(FragmentNavigation.Presenter presenter);

	void showLoginActivity();

	void showLoginPinActivity();

	void showModeSelectActivity();
}
