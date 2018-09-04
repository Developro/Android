package com.studios.uio443.cluck.presentation.mvp;

import android.content.Context;

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

        Context context();
	}

	interface Presenter {
		void onSignInVK();

		void onLogin(String email, String password);
	}
}
