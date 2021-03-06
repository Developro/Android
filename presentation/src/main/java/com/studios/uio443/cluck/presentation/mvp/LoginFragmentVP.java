package com.studios.uio443.cluck.presentation.mvp;

import com.studios.uio443.cluck.presentation.view.fragment.BaseFragment;

/**
 * Created by zundarik
 */


public interface LoginFragmentVP {
    interface View {
        void setFragment(BaseFragment fragment);

        void startActivity(Class activityClass);

        void startActivityForResult(Class activityClass, int requestCode);

        void VKSdkLogin();

        void showLoginSuccess();

        void showLoginFailed();

        void progressDialog();

        boolean validate();
    }

    interface Presenter {
        void onSignInVK();

        void onLogin(String email, String password);

        void onSignUp();

        void onLoginSuccess();

    }
}
