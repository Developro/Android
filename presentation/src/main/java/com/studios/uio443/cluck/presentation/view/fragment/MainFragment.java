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

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zundarik
 */

public class MainFragment extends BaseFragment implements MainFragmentVP.View {

    private static final String ID_KEY = "ID";
    @Inject
    MainFragmentPresenter presenter; //@FragmentScope
    private int someId;

    public MainFragment() {
    }

    public static MainFragment create(int id) {
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ID_KEY, id);
        mainFragment.setArguments(args);
        return mainFragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        someId = getArguments().getInt(ID_KEY);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(Consts.TAG, "MainFragment.onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        presenter.doSometing();
//        if (savedInstanceState == null) {
//            presenter = new MainFragmentPresenter();
//        } else {
//            presenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
//        }
    }

    @Override
    public void onResult(String result) {
        Log.d(Consts.TAG, "MainFragment.onResult");
        //Todo
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.bindView(this);
    }

    public int getSomeId() {
        return someId;
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
