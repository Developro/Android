package com.studios.uio443.cluck.presentation.internal.di.components;

import com.studios.uio443.cluck.presentation.App;
import com.studios.uio443.cluck.presentation.internal.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AppModule.class, AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<App> {

    //void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
//    Context context();
//    ThreadExecutor threadExecutor();
//    PostExecutionThread postExecutionThread();
//    UserRepository userRepository();


    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {
    }

//    @Component.Builder
//    interface Builder {


//        @BindsInstance
//        Builder context(Context context);
//или так
//        @BindsInstance
//        Builder application(Application application);


//        AppComponent build();
//    }

    //void inject(App app);
}
