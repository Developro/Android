package com.studios.uio443.cluck.presentation.structure.router;

import android.content.Context;

public interface UserListRouter {
	void showUserListFragment();

	void showUserDetails(Context context, int userId);
}