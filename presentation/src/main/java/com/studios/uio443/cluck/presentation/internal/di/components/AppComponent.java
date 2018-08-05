package com.studios.uio443.cluck.presentation.internal.di.components;

import com.studios.uio443.cluck.presentation.App;
import com.studios.uio443.cluck.presentation.internal.di.modules.AppModule;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class, AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<App> {

    //void inject(BaseActivity baseActivity);
    //void inject(MainActivity mainActivity);
    //void inject(LoginActivity loginActivity);

    //Exposed to sub-graphs.
//    Context context();
//    ThreadExecutor threadExecutor();
//    PostExecutionThread postExecutionThread();
//    UserRepository userRepository();


    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {
    }
}
