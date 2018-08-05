package com.studios.uio443.cluck.presentation.internal.di.modules;

import com.studios.uio443.cluck.presentation.internal.di.Scope.ActivityScope;
import com.studios.uio443.cluck.presentation.internal.di.Scope.FragmentScope;
import com.studios.uio443.cluck.presentation.router.LoginRouter;
import com.studios.uio443.cluck.presentation.router.impl.LoginRouterImpl;
import com.studios.uio443.cluck.presentation.view.fragment.LoginFragment;
import com.studios.uio443.cluck.presentation.view.fragment.LogoutFragment;
import com.studios.uio443.cluck.presentation.view.fragment.SignupFragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface LoginActivityModule {
	@ActivityScope
	@Binds
	LoginRouter router(LoginRouterImpl loginRouter);

//	@ActivityScope
//	@Binds
//	LoginActivityVP.Presenter provideLoginActivityPresenter(LoginActivityPresenter loginActivityPresenter);
//
//	@ActivityScope
//	@Binds
//	LoginFragmentVP.Presenter provideLoginFragmentPresenterPresenter(LoginFragmentPresenter loginFragmentPresenter);
//
//	@ActivityScope
//	@Binds
//	LogoutFragmentVP.Presenter provideLogoutFragmentPresenter(LogoutFragmentPresenter logoutFragmentPresenter);
//
//	@ActivityScope
//	@Binds
//	SignupFragmentVP.Presenter provideSignupFragmentPresenter(SignupFragmentPresenter signupFragmentPresenter);

	@FragmentScope
	@ContributesAndroidInjector(modules = {LoginFragmentModule.class})
	LoginFragment loginFragment();

	@FragmentScope
	@ContributesAndroidInjector(modules = {LoginFragmentModule.class})
	LogoutFragment logoutFragment();

	@FragmentScope
	@ContributesAndroidInjector(modules = {LoginFragmentModule.class})
	SignupFragment signupFragment();
}
