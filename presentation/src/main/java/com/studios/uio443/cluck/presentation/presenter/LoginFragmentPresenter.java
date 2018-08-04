package com.studios.uio443.cluck.presentation.presenter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.studios.uio443.cluck.domain.interactor.GetUserProfile;
import com.studios.uio443.cluck.presentation.internal.di.PerActivity;
import com.studios.uio443.cluck.presentation.mapper.UserModelDataMapper;
import com.studios.uio443.cluck.presentation.model.UserHolder;
import com.studios.uio443.cluck.presentation.model.UserModel;
import com.studios.uio443.cluck.presentation.mvp.FragmentNavigation;
import com.studios.uio443.cluck.presentation.mvp.LoginFragmentVP;
import com.studios.uio443.cluck.presentation.util.Consts;
import com.studios.uio443.cluck.presentation.view.activity.LoginPinActivity;
import com.studios.uio443.cluck.presentation.view.fragment.BaseFragment;
import com.studios.uio443.cluck.presentation.view.fragment.SignupFragment;

import javax.inject.Inject;

/**
 * Created by zundarik
 */

@PerActivity
public class LoginFragmentPresenter extends BasePresenter<UserHolder, LoginFragmentVP.View> implements
        LoginFragmentVP.Presenter,
        FragmentNavigation.Presenter {

    private boolean isLoadingData = false;

    private final GetUserProfile getUserProfileUseCase;
    private final UserModelDataMapper userModelDataMapper;

    @Inject
    public LoginFragmentPresenter(GetUserProfile getUserProfileUseCase,
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
    public void addFragment(BaseFragment fragment) {
        view().setFragment(fragment);
    }

    @Override
    public void onSignInVK() {
        view().VKSdkLogin();
    }

    @Override
    public void onLogin(String email, String password) {
        Log.d(Consts.TAG, "LoginFragmentPresenter.onLogin");

        if (!view().validate()) {
            view().showLoginFailed();
            return;
        }

        // TODO: Implement your own authentication logic here.

        UserModel user = model.authentication(email, password);

        if (user == null) {
            view().showLoginFailed();
            return;
        }

        view().showLoginSuccess();
        view().progressDialog(); //start MainActivity
    }

    @Override
    public void onSignUp() {
        Log.d(Consts.TAG, "LoginFragmentPresenter.onSignUp");
        view().setFragment(new SignupFragment());
    }

    @Override
    public void onLoginSuccess() {
        Log.d(Consts.TAG, "LoginFragmentPresenter.onLoginSuccess");
        view().showLoginSuccess();
        view().startActivityForResult(LoginPinActivity.class, Consts.REQUEST_CODE_LOGIN_PIN_ACTIVITY);
        //view().startActivity(ModeSelectActivity.class); //start MainActivity
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
