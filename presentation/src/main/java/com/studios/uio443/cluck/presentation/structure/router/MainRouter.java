package com.studios.uio443.cluck.presentation.structure.router;

import com.studios.uio443.cluck.presentation.mvp.FragmentNavigation;

public interface MainRouter {
	void showMainFragment(FragmentNavigation.Presenter presenter);

	void showSettingsFragment();

	void showAboutFragment();

	void showFeedbackFragment();

	void showUserList();
}
