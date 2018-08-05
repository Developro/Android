package com.studios.uio443.cluck.presentation.structure.router.impl;

import android.content.Context;
import android.content.Intent;
import com.studios.uio443.cluck.presentation.R;
import com.studios.uio443.cluck.presentation.structure.router.UserListRouter;
import com.studios.uio443.cluck.presentation.view.activity.UserListActivity;
import com.studios.uio443.cluck.presentation.view.activity.UserProfileActivity;
import com.studios.uio443.cluck.presentation.view.fragment.UserListFragment;
import io.reactivex.annotations.NonNull;

import javax.inject.Inject;

public class UserListRouterImpl extends BaseRouterImpl<UserListActivity> implements UserListRouter {

	@Inject
	public UserListRouterImpl(UserListActivity activity) {
		super(activity);
	}

	@Override
	public void showUserListFragment() {
		replaceFragment(R.id.fragmentContainer, new UserListFragment());
	}

	public void showUserDetails(@NonNull Context context, int userId) {
		Intent intentToLaunch = UserProfileActivity.getCallingIntent(context, userId);
		startActivity(intentToLaunch);
	}

//    public void showUserDetails(Context context, int userId) {
//        if (context != null) {
//            Intent intentToLaunch = UserProfileActivity.getCallingIntent(context, userId);
//            startActivity(intentToLaunch);
//        }
//    }

}
