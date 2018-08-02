package com.studios.uio443.cluck.presentation.mvp;

/**
 * Created by zundarik on 29.07.2018
 */

public interface MainFragmentVP {
    interface View {
        void onResult(String result);
    }

    interface Presenter {
        void doSometing();
    }
}
