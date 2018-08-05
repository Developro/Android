package com.studios.uio443.cluck.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.studios.uio443.cluck.presentation.R;
import com.studios.uio443.cluck.presentation.mvp.MainFragmentVP;
import com.studios.uio443.cluck.presentation.presenter.MainFragmentPresenter;
import com.studios.uio443.cluck.presentation.presenter.PresenterManager;
import com.studios.uio443.cluck.presentation.util.Consts;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zundarik
 */

public class MainFragment extends BaseFragment implements MainFragmentVP.View {

    MainFragmentPresenter presenter;

    public MainFragment() {
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(Consts.TAG, "MainFragment.onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (savedInstanceState == null) {
            presenter = new MainFragmentPresenter();
        } else {
            presenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.bindView(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(Consts.TAG, "MainFragment.onSaveInstanceState");
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(presenter, outState);
    }

    @Override
    public void onPause() {
        Log.d(Consts.TAG, "MainFragment.onPause");
        super.onPause();

        presenter.unbindView();
    }

    /**
     * Goes to the user list screen.
     */
    @OnClick(R.id.btn_LoadData)
    void navigateToUserList() {
        //this.navigator.navigateToUserList(getContext());
    }
}
