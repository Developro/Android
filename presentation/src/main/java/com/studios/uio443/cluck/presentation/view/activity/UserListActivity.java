/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.studios.uio443.cluck.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import com.studios.uio443.cluck.presentation.R;
import com.studios.uio443.cluck.presentation.model.UserModel;
import com.studios.uio443.cluck.presentation.structure.router.UserListRouter;
import com.studios.uio443.cluck.presentation.view.fragment.UserListFragment;

import javax.inject.Inject;

/**
 * Activity that shows a list of Users.
 */
public class UserListActivity extends BaseActivity implements UserListFragment.UserListListener {

	@Inject
	UserListRouter router;

	public static Intent getCallingIntent(Context context) {
		return new Intent(context, UserListActivity.class);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_layout);

		if (savedInstanceState == null) {
			router.showUserListFragment();
		}
	}

	@Override
	public void onUserClicked(UserModel userModel) {
		//this.navigator.navigateToUserDetails(this, userModel.getUserId());
		router.showUserDetails(this, userModel.getUserId());
	}
}
