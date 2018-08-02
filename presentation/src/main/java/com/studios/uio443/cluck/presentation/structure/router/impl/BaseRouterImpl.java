package com.studios.uio443.cluck.presentation.structure.router.impl;

import android.app.Fragment;
import android.app.FragmentManager;

import com.studios.uio443.cluck.presentation.view.activity.BaseActivity;

abstract public class BaseRouterImpl<A extends BaseActivity> {
    private A activity;
    private FragmentManager fragmentManager;

    public BaseRouterImpl(A activity) {
        this.activity = activity;
        this.fragmentManager = activity.getFragmentManager();
    }


    public void replaceFragment(int content, Fragment fragment) {
        fragmentManager.beginTransaction()
                .add(content, fragment)
                .commit();
    }
}
