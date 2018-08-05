package com.studios.uio443.cluck.presentation.internal.di.modules;

import android.content.Context;
import com.studios.uio443.cluck.data.cache.UserCache;
import com.studios.uio443.cluck.data.cache.UserCacheImpl;
import com.studios.uio443.cluck.data.executor.JobExecutor;
import com.studios.uio443.cluck.data.repository.UserDataRepository;
import com.studios.uio443.cluck.domain.executor.PostExecutionThread;
import com.studios.uio443.cluck.domain.executor.ThreadExecutor;
import com.studios.uio443.cluck.domain.repository.UserRepository;
import com.studios.uio443.cluck.presentation.App;
import com.studios.uio443.cluck.presentation.UIThread;
import com.studios.uio443.cluck.presentation.internal.di.Scope.ActivityScope;
import com.studios.uio443.cluck.presentation.structure.repo.Repository;
import com.studios.uio443.cluck.presentation.structure.repo.RepositoryImpl;
import com.studios.uio443.cluck.presentation.view.activity.LoginActivity;
import com.studios.uio443.cluck.presentation.view.activity.MainActivity;
import com.studios.uio443.cluck.presentation.view.activity.UserListActivity;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

import javax.inject.Singleton;

@Module(includes = {AndroidSupportInjectionModule.class})
public abstract class AppModule {

	@Singleton
	@Provides
	public static Context context(App app) {
		return app.getApplicationContext();
	}

//    @Singleton
//    @Provides
//    public static UserRepository userRepository(Context context) {
//        return new UserRepositoryImpl(context);
//    }

	@Singleton
	@Provides
	public static Repository repository(RepositoryImpl repository) {
		return new RepositoryImpl();
	}

	@Provides
	@Singleton
	public static ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
		return jobExecutor;
	}

	@Provides
	@Singleton
	public static PostExecutionThread providePostExecutionThread(UIThread uiThread) {
		return uiThread;
	}

	@Provides
	@Singleton
	public static UserCache provideUserCache(UserCacheImpl userCache) {
		return userCache;
	}

	@Provides
	@Singleton
	public static UserRepository provideUserRepository(UserDataRepository userDataRepository) {
		return userDataRepository;
	}

	@ActivityScope
	@ContributesAndroidInjector(modules = {MainActivityModule.class})
	abstract MainActivity mainActivityInjector();

	@ActivityScope
	@ContributesAndroidInjector(modules = {LoginActivityModule.class})
	abstract LoginActivity loginActivityInjector();

	@ActivityScope
	@ContributesAndroidInjector(modules = {UserListActivityModule.class})
	abstract UserListActivity mainUserListActivity();
}
