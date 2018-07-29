package com.studios.uio443.cluck.presentation;

import android.content.Intent;
import android.widget.Toast;

import com.squareup.leakcanary.LeakCanary;
import com.studios.uio443.cluck.presentation.internal.di.components.ApplicationComponent;
import com.studios.uio443.cluck.presentation.internal.di.components.DaggerApplicationComponent;
import com.studios.uio443.cluck.presentation.internal.di.modules.ApplicationModule;
import com.studios.uio443.cluck.presentation.view.activity.LoginActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

/**
 * Android Main Application
 */

/**
 * Created by zundarik
 */

public class Application extends android.app.Application {

    //Handling "AccessToken invalid"
    //Для обработки ситуаций, когда AccessToken становится невалиден (например, пользователь сменил пароль)
    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                // VKAccessToken is invalid
                Toast.makeText(Application.this, "AccessToken invalidated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Application.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.initializeLeakDetection();

        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);

    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }
}
