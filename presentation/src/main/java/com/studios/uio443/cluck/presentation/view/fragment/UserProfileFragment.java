/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.studios.uio443.cluck.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fernandocejas.arrow.checks.Preconditions;
import com.studios.uio443.cluck.presentation.R;
import com.studios.uio443.cluck.presentation.model.UserModel;
import com.studios.uio443.cluck.presentation.presenter.UserProfilePresenter;
import com.studios.uio443.cluck.presentation.view.UserProfileView;
import com.studios.uio443.cluck.presentation.view.component.AutoLoadImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Fragment that shows details of a certain user.
 */
public class UserProfileFragment extends BaseFragment implements UserProfileView {
	private static final String PARAM_USER_ID = "param_user_id";

	@Inject
	UserProfilePresenter userProfilePresenter;

	@BindView(R.id.iv_cover)
	AutoLoadImageView iv_cover;
	@BindView(R.id.tv_fullname)
	TextView tv_fullname;
	@BindView(R.id.tv_points)
	TextView tv_points;
	@BindView(R.id.tv_votes)
	TextView tv_votes;
	@BindView(R.id.rl_progress)
	RelativeLayout rl_progress;
	@BindView(R.id.rl_retry)
	RelativeLayout rl_retry;
	@BindView(R.id.bt_retry)
	Button bt_retry;
	private Unbinder unbinder;

	public UserProfileFragment() {
		setRetainInstance(true);
	}

	public static UserProfileFragment forUser(int userId) {
		final UserProfileFragment userDetailsFragment = new UserProfileFragment();
		final Bundle arguments = new Bundle();
		arguments.putInt(PARAM_USER_ID, userId);
		userDetailsFragment.setArguments(arguments);
		return userDetailsFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_user_profile;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		unbinder = ButterKnife.bind(this, view);
		this.userProfilePresenter.setView(this);
		if (savedInstanceState == null) {
			this.loadUserDetails();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		this.userProfilePresenter.resume();
	}

	@Override
	public void onPause() {
		super.onPause();
		this.userProfilePresenter.pause();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.userProfilePresenter.destroy();
	}

	@Override
	public void renderUser(UserModel user) {
		if (user != null) {
			this.iv_cover.setImageUrl(user.getCoverUrl());
			this.tv_fullname.setText(user.getFullName());
			this.tv_points.setText(user.getPoints());
			this.tv_votes.setText(user.getVotes());
		}
	}

	@Override
	public void showLoading() {
		this.rl_progress.setVisibility(View.VISIBLE);
		this.getActivity().setProgressBarIndeterminateVisibility(true);
	}

	@Override
	public void hideLoading() {
		this.rl_progress.setVisibility(View.GONE);
		this.getActivity().setProgressBarIndeterminateVisibility(false);
	}

	@Override
	public void showRetry() {
		this.rl_retry.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideRetry() {
		this.rl_retry.setVisibility(View.GONE);
	}

	@Override
	public void showError(String message) {
		this.showToastMessage(message);
	}

	@Override
	public Context context() {
		return getActivity().getApplicationContext();
	}

	/**
	 * Load user details.
	 */
	private void loadUserDetails() {
		if (this.userProfilePresenter != null) {
			this.userProfilePresenter.initialize(currentUserId());
		}
	}

	/**
	 * Get current user id from fragments arguments.
	 */
	private int currentUserId() {
		final Bundle arguments = getArguments();
		Preconditions.checkNotNull(arguments, "Fragment arguments cannot be null");
		return arguments.getInt(PARAM_USER_ID);
	}

	@OnClick(R.id.bt_retry)
	void onButtonRetryClick() {
		UserProfileFragment.this.loadUserDetails();
	}
}
