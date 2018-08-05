package com.studios.uio443.cluck.presentation.presenter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.studios.uio443.cluck.presentation.internal.di.Scope.ActivityScope;
import com.studios.uio443.cluck.presentation.model.UserHolder;
import com.studios.uio443.cluck.presentation.mvp.FragmentNavigation;
import com.studios.uio443.cluck.presentation.mvp.LoginActivityVP;

import javax.inject.Inject;

/**
 * Created by zundarik
 */

@ActivityScope
public class LoginActivityPresenter extends BasePresenter<UserHolder, LoginActivityVP.View> implements
				LoginActivityVP.Presenter,
				FragmentNavigation.Presenter {

	private boolean isLoadingData = false;

	@Inject
	public LoginActivityPresenter() {
	}

	@Override
	protected void updateView() {
		// Business logic is in the presenter
		//view().updateText(model.getText() + " " + count);
	}

	@Override
	public void bindView(@NonNull LoginActivityVP.View view) {
		super.bindView(view);

		// Let's not reload data if it's already here
		if (model == null && !isLoadingData) {
			setModel(UserHolder.getInstance());
			//loadData(); // если нужен запрос к серверу
		}
	}

	private void loadData() {
		isLoadingData = true;
		new LoadDataTask().execute();
	}

//    @Override
//    public void addFragment(BaseFragment fragment) {
//        view().setFragment(fragment);
//    }

//    @Override
//    public void showLogin() {
//        Log.d(Consts.TAG, "LoginActivityPresenter.showLogin");
//        view().setFragment(new LoginFragment());
//    }
//
//    @Override
//    public void showLogout() {
//        Log.d(Consts.TAG, "LoginActivityPresenter.showLogout");
//        view().setFragment(new LogoutFragment());
//    }

//    @Override
//    public void startModeSelectActivity() {
//        Log.d(Consts.TAG, "LoginActivityPresenter.startModeSelectActivity");
//        view().startActivity(ModeSelectActivity.class);
//    }

	// It's OK for this class not to be static and to keep a reference to the Presenter, as this
	// is retained during orientation changes and is lightweight (has no activity/view reference)
	@SuppressLint("StaticFieldLeak")
	private class LoadDataTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			//SystemClock.sleep(3000);
			//длительные процесс
			//TODO получение данных от сервера
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			//setModel(new MainModel());
			isLoadingData = false;
		}
	}
}
