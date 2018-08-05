package com.studios.uio443.cluck.presentation.internal.di.modules;

import com.studios.uio443.cluck.presentation.internal.di.Scope.ActivityScope;
import com.studios.uio443.cluck.presentation.mvp.LoginActivityVP;
import com.studios.uio443.cluck.presentation.mvp.LoginFragmentVP;
import com.studios.uio443.cluck.presentation.mvp.LogoutFragmentVP;
import com.studios.uio443.cluck.presentation.mvp.SignupFragmentVP;
import com.studios.uio443.cluck.presentation.presenter.LoginActivityPresenter;
import com.studios.uio443.cluck.presentation.presenter.LoginFragmentPresenter;
import com.studios.uio443.cluck.presentation.presenter.LogoutFragmentPresenter;
import com.studios.uio443.cluck.presentation.presenter.SignupFragmentPresenter;
import com.studios.uio443.cluck.presentation.structure.router.LoginRouter;
import com.studios.uio443.cluck.presentation.structure.router.impl.LoginRouterImpl;
import dagger.Binds;
import dagger.Module;

@Module
public interface LoginActivityModule {
	@ActivityScope
	@Binds
	LoginRouter router(LoginRouterImpl loginRouter);

	@ActivityScope
	@Binds
	LoginActivityVP.Presenter provideLoginActivityPresenter(LoginActivityPresenter loginActivityPresenter);

	@ActivityScope
	@Binds
	LoginFragmentVP.Presenter provideLoginFragmentPresenterPresenter(LoginFragmentPresenter loginFragmentPresenter);

	@ActivityScope
	@Binds
	LogoutFragmentVP.Presenter provideLogoutFragmentPresenter(LogoutFragmentPresenter logoutFragmentPresenter);

	@ActivityScope
	@Binds
	SignupFragmentVP.Presenter provideSignupFragmentPresenter(SignupFragmentPresenter signupFragmentPresenter);
}
