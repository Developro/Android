package com.studios.uio443.cluck.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import com.studios.uio443.cluck.presentation.R;
import com.studios.uio443.cluck.presentation.mvp.LoginActivityVP;
import com.studios.uio443.cluck.presentation.presenter.LoginActivityPresenter;
import com.studios.uio443.cluck.presentation.presenter.PresenterManager;
import com.studios.uio443.cluck.presentation.router.LoginRouter;
import com.studios.uio443.cluck.presentation.util.Consts;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import javax.inject.Inject;

/**
 * Created by zundarik
 */

public class LoginActivity extends BaseActivity implements
				LoginActivityVP.View {

	@Inject
	LoginRouter router;
	@Inject
	LoginActivityPresenter presenter;

	private boolean isResumed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(Consts.TAG, "LoginActivity.onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		presenter.bindView(this);

		VKSdk.wakeUpSession(this, new VKCallback<VKSdk.LoginState>() {
			@Override
			public void onResult(VKSdk.LoginState res) {
				if (isResumed) {
					switch (res) {
						case LoggedOut:
							router.showLoginFragment();
							break;
						case LoggedIn:
							router.showLogoutFragment();
							break;
						case Pending:
							break;
						case Unknown:
							break;
					}
				}
			}

			@Override
			public void onError(VKError error) {
			}
		});

	}

	@Override
	protected void onResume() {
		Log.d(Consts.TAG, "LoginActivity.onResume");
		super.onResume();
		//presenter.bindView(this);
		isResumed = true;
		if (VKSdk.isLoggedIn()) {
			router.showLogoutFragment();
		} else {
			router.showLoginFragment();
		}
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		Log.d(Consts.TAG, "LoginActivity.onSaveInstanceState");
		super.onSaveInstanceState(outState);

		PresenterManager.getInstance().savePresenter(presenter, outState);
	}

	@Override
	protected void onPause() {
		Log.d(Consts.TAG, "LoginActivity.onPause");
		isResumed = false;
		super.onPause();
		//presenter.unbindView();
	}

	@Override
	protected void onDestroy() {
		Log.d(Consts.TAG, "LoginActivity.onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(Consts.TAG, "LoginActivity.onActivityResult");
		presenter.bindView(this);

		switch (requestCode) {
			case Consts.REQUEST_CODE_LOGIN_PIN_ACTIVITY: {
				Log.i(Consts.TAG, "LoginActivity.onActivityResult.REQUEST_CODE_LOGIN_PIN_ACTIVITY");
				if (resultCode == RESULT_OK) {
					router.showModeSelectActivity();
				}
				break;
			}
		}

		VKCallback<VKAccessToken> callback = new VKCallback<VKAccessToken>() {
			@Override
			public void onResult(VKAccessToken res) {
				Log.d(Consts.TAG, "LoginActivity.onActivityResult.VKCallback.onResult");
				// User passed Authorization
				router.showModeSelectActivity();
			}

			@Override
			public void onError(VKError error) {
				Log.d(Consts.TAG, "LoginActivity.onActivityResult.VKCallback.onError");
				// User didn't pass Authorization
			}
		};

		if (!VKSdk.onActivityResult(requestCode, resultCode, data, callback)) {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
}
