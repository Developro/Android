package com.studios.uio443.cluck.presentation.internal.di.modules;

import com.studios.uio443.cluck.presentation.mvp.LoginFragmentVP;
import com.studios.uio443.cluck.presentation.mvp.LogoutFragmentVP;
import com.studios.uio443.cluck.presentation.mvp.SignupFragmentVP;
import com.studios.uio443.cluck.presentation.view.fragment.LoginFragment;
import com.studios.uio443.cluck.presentation.view.fragment.LogoutFragment;
import com.studios.uio443.cluck.presentation.view.fragment.SignupFragment;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class LoginFragmentModule {

	@Binds
	public abstract LoginFragmentVP.View loginFragmentView(LoginFragment loginFragment);

	@Binds
	public abstract LogoutFragmentVP.View logoutFragmentView(LogoutFragment logoutFragment);

	@Binds
	public abstract SignupFragmentVP.View signupFragmentView(SignupFragment signupFragment);
}
