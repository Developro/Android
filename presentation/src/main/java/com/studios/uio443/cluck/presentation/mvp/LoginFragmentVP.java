package com.studios.uio443.cluck.presentation.mvp;

/**
 * Created by zundarik
 */

public interface LoginFragmentVP {
	interface View {
		void VKSdkLogin();

		void showLoginFailed();

		void showProgressDialog();

		void showLoginSuccess();

		boolean validate();
	}

	interface Presenter {
		void onSignInVK();

		void onLogin(String email, String password);
	}
}
