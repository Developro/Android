package com.studios.uio443.cluck.presentation.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.studios.uio443.cluck.presentation.R;
import com.studios.uio443.cluck.presentation.mvp.LoginFragmentVP;
import com.studios.uio443.cluck.presentation.presenter.LoginFragmentPresenter;
import com.studios.uio443.cluck.presentation.presenter.PresenterManager;
import com.studios.uio443.cluck.presentation.router.LoginRouter;
import com.studios.uio443.cluck.presentation.util.Consts;
import com.studios.uio443.cluck.presentation.util.LoginValidator;
import com.studios.uio443.cluck.presentation.view.activity.MainActivity;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zundarik
 */

public class LoginFragment extends BaseFragment implements LoginFragmentVP.View {

	@Inject
	LoginRouter router;
	@Inject
	LoginFragmentPresenter loginFragmentPresenter;

	private static final String LOGIN = "LOGIN";
	private static final String PASSWORD = "PASSWORD";
	private static final String[] sMyScope = new String[]{
					VKScope.FRIENDS,
					VKScope.WALL,
					VKScope.PHOTOS,
					VKScope.NOHTTPS,
					VKScope.MESSAGES,
					VKScope.DOCS
	};

	//используем butterknife
	//https://jakewharton.github.io/butterknife/
	//Обзор Butter Knife - https://startandroid.ru/ru/blog/470-butter-knife.html
	@BindView(R.id.login_email_layout)
	TextInputLayout loginEmailLayout;
	@BindView(R.id.login_password_layout)
	TextInputLayout loginPasswordLayout;
	@BindView(R.id.login_email_input)
	EditText loginEmailInput;
	@BindView(R.id.login_password_input)
	EditText loginPasswordInput;
	@BindView(R.id.sign_in_button_VK)
	ImageButton btnSignInVK;
	@BindView(R.id.btn_login)
	Button btnLogin;
	@BindView(R.id.link_signup)
	TextView linkSignUp;

	public LoginFragment() {
		super();
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_login;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		Log.d(Consts.TAG, "LoginFragment.onViewCreated");
		super.onViewCreated(view, savedInstanceState);
		ButterKnife.bind(this, view);

		//TODO чтение последнего логина из префа

		if (savedInstanceState != null) {
			loginEmailInput.setText(savedInstanceState.getString(LOGIN, ""));
			loginPasswordInput.setText(savedInstanceState.getString(LOGIN, ""));
		}
		loginFragmentPresenter.bindView(this);

		//Если у пользователя не установлено приложение ВКонтакте,
		// то SDK будет использовать авторизацию через новую Activity при помощи OAuth.
		btnSignInVK.setOnClickListener(v -> loginFragmentPresenter.onSignInVK());

		btnLogin.setOnClickListener(v -> {
			String email = loginEmailInput.getText().toString();
			String password = loginPasswordInput.getText().toString();

			loginFragmentPresenter.onLogin(email, password);
		});

		linkSignUp.setOnClickListener(v -> router.showSignupFragment());
	}

	@Override
	public void onResume() {
		Log.d(Consts.TAG, "LoginFragment.onResume");
		super.onResume();

		//TODO запись последнего логина в преф
		//presenter.bindView(this);
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		Log.d(Consts.TAG, "LoginFragment.onSaveInstanceState");
		super.onSaveInstanceState(outState);

		outState.putString(LOGIN, loginEmailInput.getText().toString());
		outState.putString(PASSWORD, loginPasswordInput.getText().toString());
		PresenterManager.getInstance().savePresenter(loginFragmentPresenter, outState);
	}

	@Override
	public void onPause() {
		Log.d(Consts.TAG, "LoginFragment.onPause");
		super.onPause();

		//presenter.unbindView();
	}

	@Override
	public void showProgressDialog() {
		btnLogin.setEnabled(true);
		final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme);
		progressDialog.setIndeterminate(true);
		progressDialog.setMessage(getString(R.string.authenticating));
		progressDialog.show();

		new android.os.Handler().postDelayed(
						() -> {
							// On complete call either onLoginSuccess or onLoginFailed

							btnLogin.setEnabled(true);
							router.showLoginPinActivity();
							// onLoginFailed();
							progressDialog.dismiss();
						}, 3000);
	}

    @Override
    public void showLoginSuccess() {
        Intent mainActivity = new Intent(getActivity(), MainActivity.class);
        mainActivity
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().startActivity(mainActivity);
        getActivity().finish();
    }

	@Override
	public void showLoginFailed() {
		Log.e(Consts.TAG, "LoginFragment.showLoginFailed");
		Toast.makeText(getActivity(), getString(R.string.login_failed), Toast.LENGTH_LONG).show();

        btnLogin.setEnabled(true);
	}

	@Override
	public boolean validate() {
		boolean valid;

		LoginValidator loginValidator = new LoginValidator(getActivity(), loginEmailInput.getText().toString(), loginPasswordInput.getText().toString());

		valid = loginValidator.validate();

		if (!valid) {
			loginEmailInput.setError(loginValidator.getLoginError());
			loginPasswordInput.setError(loginValidator.getPasswordError());
		}

		return valid;
	}

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

	@Override
	public void VKSdkLogin() {
		VKSdk.login(getActivity(), sMyScope);
	}
}
