package com.studios.uio443.cluck.presentation.structure.router.impl;

import com.studios.uio443.cluck.presentation.R;
import com.studios.uio443.cluck.presentation.structure.router.MainRouter;
import com.studios.uio443.cluck.presentation.view.activity.MainActivity;
import com.studios.uio443.cluck.presentation.view.fragment.MainFragment;

import javax.inject.Inject;

public class MainRouterImpl extends BaseRouterImpl<MainActivity> implements MainRouter {

    @Inject
    public MainRouterImpl(MainActivity activity) {
        super(activity);
    }


    @Override
    public void showSomeScreen(int id) {
        replaceFragment(R.id.main_container, MainFragment.create(id));
    }
}
