package com.studios.uio443.cluck.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.studios.uio443.cluck.presentation.model.UserHolder;
import com.studios.uio443.cluck.presentation.mvp.MainFragmentVP;
import com.studios.uio443.cluck.presentation.util.Consts;

public class MainFragmentPresenter extends BasePresenter<UserHolder, MainFragmentVP.View>
        implements MainFragmentVP.Presenter {

    @Override
    protected void updateView() {
        Log.d(Consts.TAG, "NoteFragmentPresenter.updateView");
        // Business logic is in the presenter
    }

    @Override
    public void bindView(@NonNull MainFragmentVP.View view) {
        super.bindView(view);

        // Let's not reload data if it's already here
        if (model == null) {
            setModel(UserHolder.getInstance());
        }
    }
}
