package com.studios.uio443.cluck.presentation.structure.router.impl;

import com.studios.uio443.cluck.presentation.R;
import com.studios.uio443.cluck.presentation.mvp.FragmentNavigation;
import com.studios.uio443.cluck.presentation.structure.router.MainRouter;
import com.studios.uio443.cluck.presentation.view.activity.MainActivity;
import com.studios.uio443.cluck.presentation.view.activity.UserListActivity;
import com.studios.uio443.cluck.presentation.view.fragment.AboutFragment;
import com.studios.uio443.cluck.presentation.view.fragment.FeedbackFragment;
import com.studios.uio443.cluck.presentation.view.fragment.MainFragment;
import com.studios.uio443.cluck.presentation.view.fragment.SettingsFragment;

import javax.inject.Inject;

public class MainRouterImpl extends BaseRouterImpl<MainActivity> implements MainRouter {

	@Inject
	public MainRouterImpl(MainActivity activity) {
		super(activity);
	}

	@Override
	public void showMainFragment(FragmentNavigation.Presenter presenter) {
		replaceFragment(R.id.main_container, new MainFragment(), presenter);
	}

	@Override
	public void showSettingsFragment() {
		replaceFragment(R.id.main_container, new SettingsFragment());
	}

	@Override
	public void showAboutFragment() {
		replaceFragment(R.id.main_container, new AboutFragment());
	}

	@Override
	public void showFeedbackFragment() {
		replaceFragment(R.id.main_container, new FeedbackFragment());
	}

	public void showUserList() {
		startActivity(UserListActivity.class);
	}
}