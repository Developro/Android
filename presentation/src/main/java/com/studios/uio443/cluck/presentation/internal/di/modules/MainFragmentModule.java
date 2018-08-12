package com.studios.uio443.cluck.presentation.internal.di.modules;

import com.studios.uio443.cluck.presentation.mvp.MainFragmentVP;
import com.studios.uio443.cluck.presentation.view.UserProfileView;
import com.studios.uio443.cluck.presentation.view.fragment.MainFragment;
import com.studios.uio443.cluck.presentation.view.fragment.UserProfileFragment;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainFragmentModule {

	@Binds
	public abstract MainFragmentVP.View mainFragmentView(MainFragment mainFragment);

	@Binds
	public abstract UserProfileView userProfileViewFragment(UserProfileFragment userProfileFragment);
}
