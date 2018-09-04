package com.studios.uio443.cluck.presentation.util;

import android.content.Context;

import com.studios.uio443.cluck.presentation.R;

public class LoginValidator {

    private String user;
    private String password;
    private Context context;
    private String loginError;
    private String passwordError;

    public LoginValidator(Context context, String user, String password) {
        this.context = context;
        this.user = user;
        this.password = password;
    }

    public boolean validate() {
        boolean valid = true;
        loginError = null;
        passwordError = null;

        if (user.isEmpty()
            // del email validate (def user is "user")
            //|| !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                ) {
            loginError = context.getString(R.string.enter_valid_email);
            valid = false;
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordError = context.getString(R.string.password_length_error);
            valid = false;
        }

        return valid;
    }

    public String getLoginError() {
        return loginError;
    }

    public String getPasswordError() {
        return passwordError;
    }
}
