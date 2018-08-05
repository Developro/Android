/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.studios.uio443.cluck.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.studios.uio443.cluck.presentation.R;
import com.studios.uio443.cluck.presentation.model.UserModel;
import com.studios.uio443.cluck.presentation.presenter.UserListPresenter;
import com.studios.uio443.cluck.presentation.view.UserListView;
import com.studios.uio443.cluck.presentation.view.adapter.UsersAdapter;
import com.studios.uio443.cluck.presentation.view.adapter.UsersLayoutManager;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Fragment that shows a list of Users.
 */
public class UserListFragment extends BaseFragment implements UserListView {

	@Inject
	UserListPresenter userListPresenter;
	@Inject
	UsersAdapter usersAdapter;
	@BindView(R.id.rv_users)
	RecyclerView rv_users;
	@BindView(R.id.rl_progress)
	RelativeLayout rl_progress;
	@BindView(R.id.rl_retry)
	RelativeLayout rl_retry;
	@BindView(R.id.bt_retry)
	Button bt_retry;
	private Unbinder unbinder;
	private UserListListener userListListener;
	private UsersAdapter.OnItemClickListener onItemClickListener =
					new UsersAdapter.OnItemClickListener() {
						@Override
						public void onUserItemClicked(UserModel userModel) {
							if (UserListFragment.this.userListPresenter != null && userModel != null) {
								UserListFragment.this.userListPresenter.onUserClicked(userModel);
							}
						}
					};

	public UserListFragment() {
		setRetainInstance(true);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof UserListListener) {
			this.userListListener = (UserListListener) context;
		}
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_user_list;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		unbinder = ButterKnife.bind(this, view);

		setupRecyclerView();
		this.userListPresenter.setView(this);
		if (savedInstanceState == null) {
			this.loadUserList();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		this.userListPresenter.resume();
	}

	@Override
	public void onPause() {
		super.onPause();
		this.userListPresenter.pause();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		rv_users.setAdapter(null);
		unbinder.unbind();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.userListPresenter.destroy();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		this.userListListener = null;
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
	public void renderUserList(Collection<UserModel> userModelCollection) {
		if (userModelCollection != null) {
			this.usersAdapter.setUsersCollection(userModelCollection);
		}
	}

	@Override
	public void viewUser(UserModel userModel) {
		if (this.userListListener != null) {
			this.userListListener.onUserClicked(userModel);
		}
	}

	@Override
	public void showError(String message) {
		this.showToastMessage(message);
	}

	@Override
	public Context context() {
		return this.getActivity().getApplicationContext();
	}

	private void setupRecyclerView() {
		this.usersAdapter.setOnItemClickListener(onItemClickListener);
		this.rv_users.setLayoutManager(new UsersLayoutManager(context()));
		this.rv_users.setAdapter(usersAdapter);
	}

	/**
	 * Loads all users.
	 */
	private void loadUserList() {
		this.userListPresenter.initialize();
	}

	@OnClick(R.id.bt_retry)
	void onButtonRetryClick() {
		UserListFragment.this.loadUserList();
	}

	/**
	 * Interface for listening user list events.
	 */
	public interface UserListListener {
		void onUserClicked(final UserModel userModel);
	}
}
