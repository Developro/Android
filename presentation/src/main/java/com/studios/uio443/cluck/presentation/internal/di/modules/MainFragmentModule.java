package com.studios.uio443.cluck.presentation.internal.di.modules;

import com.studios.uio443.cluck.presentation.mvp.MainFragmentVP;
import com.studios.uio443.cluck.presentation.view.fragment.MainFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class MainFragmentModule {

    @Provides
    @Named("someId")
    public static int provideSomeId(MainFragment mainFragment) {
        return mainFragment.getSomeId();
    }

    @Binds
    public abstract MainFragmentVP.View mainFragmentView(MainFragment mainFragment);
}
