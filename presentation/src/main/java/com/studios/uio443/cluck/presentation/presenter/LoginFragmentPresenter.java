package com.studios.uio443.cluck.presentation.presenter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import com.studios.uio443.cluck.domain.User;
import com.studios.uio443.cluck.domain.interactor.DefaultObserver;
import com.studios.uio443.cluck.domain.interactor.GetUserProfile;
import com.studios.uio443.cluck.domain.interactor.GetUserProfile.Params;
import com.studios.uio443.cluck.presentation.internal.di.Scope.ActivityScope;
import com.studios.uio443.cluck.presentation.mapper.UserModelDataMapper;
import com.studios.uio443.cluck.presentation.model.UserHolder;
import com.studios.uio443.cluck.presentation.mvp.FragmentNavigation;
import com.studios.uio443.cluck.presentation.mvp.LoginFragmentVP;
import com.studios.uio443.cluck.presentation.util.Consts;

import javax.inject.Inject;

/**
 * Created by zundarik
 */

@ActivityScope
public class LoginFragmentPresenter extends BasePresenter<UserHolder, LoginFragmentVP.View> implements
				LoginFragmentVP.Presenter,
				FragmentNavigation.Presenter {

	private boolean isLoadingData = false;

	private final GetUserProfile getUserProfileUseCase;
    private final UserModelDataMapper userModelDataMapper;

	// инжектим фрагмент презентер
    @Inject
    LoginFragmentPresenter(GetUserProfile getUserProfileUseCase,
                           UserModelDataMapper userModelDataMapper) {
        this.getUserProfileUseCase = getUserProfileUseCase;
        this.userModelDataMapper = userModelDataMapper;
    }

	@Override
	protected void updateView() {
		// Business logic is in the presenter
		//view().updateText(model.getText() + " " + count);
	}

	@Override
	public void bindView(@NonNull LoginFragmentVP.View view) {
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

	@Override
	public void onSignInVK() {
		view().VKSdkLogin();
	}

	@Override
	public void onLogin(String user, String password) {
		Log.d(Consts.TAG, "LoginFragmentPresenter.onLogin");

		if (!view().validate()) {
			view().showLoginFailed();
			return;
		}

		this.getUserProfileUseCase.execute(new UserProfileObserver(), Params.auth(user, password));
	}

	private final class UserProfileObserver extends DefaultObserver<User> {

        @Override
        public void onComplete() {
            // если все успешно, то у нас есть userModel в userHolder, можно действовать дальше
            if (UserHolder.getInstance().getUser() == null) {
                view().showLoginFailed();
                return;
            }

            view().showProgressDialog(); //start MainActivity
        }

        @Override
        public void onError(Throwable e) {
            // пришел кривой статус, ругаемся на логин
            view().showLoginFailed();
        }

        @Override
        public void onNext(User user) {
            // тут берем юзера из ретрофита и добавляем его в юзерхолдер
            UserHolder.getInstance().setUser(LoginFragmentPresenter.this.userModelDataMapper.transform(user));
        }
    }
    
	// It's OK for this class not to be static and to keep a reference to the Presenter, as this
	// is retained during orientation changes and is lightweight (has no activity/view reference)
	@SuppressLint("StaticFieldLeak")
	private class LoadDataTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			//SystemClock.sleep(3000);
			//TODO получение данных из интернета с сервера
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			//setModel(new MainModel());
			isLoadingData = false;
		}
	}
}
