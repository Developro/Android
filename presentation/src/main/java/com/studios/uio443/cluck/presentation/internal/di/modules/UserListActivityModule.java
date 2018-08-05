package com.studios.uio443.cluck.presentation.internal.di.modules;

import com.studios.uio443.cluck.presentation.internal.di.Scope.ActivityScope;
import com.studios.uio443.cluck.presentation.router.UserListRouter;
import com.studios.uio443.cluck.presentation.router.impl.UserListRouterImpl;
import dagger.Binds;
import dagger.Module;

@Module
public interface UserListActivityModule {
	@ActivityScope
	@Binds
	UserListRouter router(UserListRouterImpl userListRouterRouter);

}
