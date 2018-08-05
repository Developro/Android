package com.studios.uio443.cluck.presentation.mvp;

/**
 * Created by zundarik
 */

public interface SignupFragmentVP {
	interface View {
		void showSignupFailed();

		void progressDialog();

		boolean validate();
	}

	interface Presenter {

		void onSignup(String username, String email, String password);

	}
}
