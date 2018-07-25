package com.studios.uio443.cluck.presentation.presenter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.studios.uio443.cluck.presentation.model.User;
import com.studios.uio443.cluck.presentation.model.UserHolder;
import com.studios.uio443.cluck.presentation.mvp.FragmentNavigation;
import com.studios.uio443.cluck.presentation.mvp.SignupFragmentVP;
import com.studios.uio443.cluck.presentation.util.Consts;
import com.studios.uio443.cluck.presentation.view.activity.LoginActivity;
import com.studios.uio443.cluck.presentation.view.activity.LoginPinActivity;
import com.studios.uio443.cluck.presentation.view.fragment.BaseFragment;

public class SignupFragmentPresenter extends BasePresenter<UserHolder, SignupFragmentVP.View> implements
        SignupFragmentVP.Presenter,
        FragmentNavigation.Presenter {

    private boolean isLoadingData = false;

    @Override
    protected void updateView() {
        // Business logic is in the presenter
        //view().updateText(model.getText() + " " + count);
    }

    @Override
    public void bindView(@NonNull SignupFragmentVP.View view) {
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
    public void onSignup(String username, String email, String password) {
        Log.d(Consts.TAG, "SignupFragmentPresenter.onSignup");
        if (!view().validate()) {
            view().showSignupFailed();
            return;
        }

        // TODO: Implement your own signup logic here.
        User user = model.signup(email, password, username);

        if (user == null) {
            view().showSignupFailed();
            return;
        }

        view().showSignupSuccess();
        view().progressDialog(); //start MainActivity
    }

    @Override
    public void linkLogin() {
        //TODO проверить и заменить на finish fragment
        view().startActivity(LoginActivity.class);
    }

    @Override
    public void onSignupSuccess() {
        view().showSignupSuccess();
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
            //TODO получение данных из интернета
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //setModel(new MainModel());
            isLoadingData = false;
        }
    }
}
