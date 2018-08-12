package com.studios.uio443.cluck.presentation.internal.di.modules;

import com.studios.uio443.cluck.presentation.internal.di.Scope.ActivityScope;
import com.studios.uio443.cluck.presentation.internal.di.Scope.FragmentScope;
import com.studios.uio443.cluck.presentation.router.MainRouter;
import com.studios.uio443.cluck.presentation.router.impl.MainRouterImpl;
import com.studios.uio443.cluck.presentation.view.fragment.MainFragment;
import com.studios.uio443.cluck.presentation.view.fragment.UserProfileFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface MainActivityModule {
	@ActivityScope
	@Binds
	MainRouter router(MainRouterImpl mainRouter);

//	@ActivityScope
//	@Binds
//	MainActivityVP.Presenter provideMainActivityPresenter(MainActivityPresenter mainActivityPresenter);
//
//	@ActivityScope
//	@Binds
//	MainFragmentVP.Presenter provideMainFragmentPresenter(MainFragmentPresenter mainFragmentPresenter);

	@FragmentScope
	@ContributesAndroidInjector(modules = {MainFragmentModule.class})
	MainFragment mainFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = {UserFragmentModule.class})
    UserProfileFragment userProfileFragment();
}
