package com.studios.uio443.cluck.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.studios.uio443.cluck.presentation.R;
import com.studios.uio443.cluck.presentation.mvp.LogoutFragmentVP;
import com.studios.uio443.cluck.presentation.presenter.LogoutFragmentPresenter;
import com.studios.uio443.cluck.presentation.presenter.PresenterManager;
import com.studios.uio443.cluck.presentation.router.LoginRouter;
import com.studios.uio443.cluck.presentation.util.Consts;
import com.vk.sdk.VKSdk;

import javax.inject.Inject;

/**
 * Created by zundarik
 */

public class LogoutFragment extends BaseFragment implements LogoutFragmentVP.View {

	@Inject
	LoginRouter router;
	@Inject
	LogoutFragmentPresenter logoutFragmentPresenter;

	@BindView(R.id.continue_button)
	Button btnContinue;
	@BindView(R.id.logout)
	Button btnLogout;

	public LogoutFragment() {
		super();
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_logout;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		Log.d(Consts.TAG, "LoginFragment.onCreateView");
		super.onViewCreated(view, savedInstanceState);
		ButterKnife.bind(this, view);

		logoutFragmentPresenter.bindView(this);

		btnContinue.setOnClickListener(v -> router.showModeSelectActivity());

		btnLogout.setOnClickListener(v -> {
			VKSdk.logout();
			if (!VKSdk.isLoggedIn()) {
				router.showLoginFragment();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();

		//presenter.bindView(this);
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		Log.d(Consts.TAG, "LogoutFragment.onSaveInstanceState");
		super.onSaveInstanceState(outState);
		PresenterManager.getInstance().savePresenter(logoutFragmentPresenter, outState);
	}

	@Override
	public void onPause() {
		Log.d(Consts.TAG, "LogoutFragment.onPause");
		super.onPause();

		//presenter.unbindView();
	}
}
