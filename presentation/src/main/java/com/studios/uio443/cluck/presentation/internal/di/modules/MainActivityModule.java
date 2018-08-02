package com.studios.uio443.cluck.presentation.internal.di.modules;

import com.studios.uio443.cluck.presentation.internal.di.Scope.ActivityScope;
import com.studios.uio443.cluck.presentation.internal.di.Scope.FragmentScope;
import com.studios.uio443.cluck.presentation.structure.facade.Facade;
import com.studios.uio443.cluck.presentation.structure.facade.FacadeImpl;
import com.studios.uio443.cluck.presentation.structure.router.MainRouter;
import com.studios.uio443.cluck.presentation.structure.router.impl.MainRouterImpl;
import com.studios.uio443.cluck.presentation.view.fragment.MainFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface MainActivityModule {
    @ActivityScope
    @Binds
    Facade facade(FacadeImpl facade);

    @ActivityScope
    @Binds
    MainRouter router(MainRouterImpl mainRouter);

    @FragmentScope
    @ContributesAndroidInjector(modules = {MainFragmentModule.class})
    MainFragment mainFragment();
}
