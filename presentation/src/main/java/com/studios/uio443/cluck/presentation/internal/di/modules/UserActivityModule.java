package com.studios.uio443.cluck.presentation.internal.di.modules;

import com.studios.uio443.cluck.presentation.internal.di.Scope.FragmentScope;
import com.studios.uio443.cluck.presentation.view.fragment.UserProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface UserActivityModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = {UserFragmentModule.class})
    UserProfileFragment userProfileFragment();

}
