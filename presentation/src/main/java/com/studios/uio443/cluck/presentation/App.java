package com.studios.uio443.cluck.presentation;

import android.content.Intent;
import android.widget.Toast;

import com.squareup.leakcanary.LeakCanary;
import com.studios.uio443.cluck.data.util.SharedPreferencesUtil;
import com.studios.uio443.cluck.presentation.internal.di.components.DaggerAppComponent;
import com.studios.uio443.cluck.presentation.view.activity.LoginActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Android Main App
 */

/**
 * Created by zundarik
 */

public class App extends DaggerApplication {

	//Dagger 2.11 & Android
	//https://habrahabr.ru/post/335940/

	//Handling "AccessToken invalid"
	//Для обработки ситуаций, когда AccessToken становится невалиден (например, пользователь сменил пароль)
	VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
		@Override
		public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
			if (newToken == null) {
				// VKAccessToken is invalid
				Toast.makeText(App.this, "AccessToken invalidated", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(App.this, LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();

		this.initializeLeakDetection();

		vkAccessTokenTracker.startTracking();
		VKSdk.initialize(this);

		// init shared pref
		SharedPreferencesUtil.initSharedPreferences(this.getApplicationContext());
	}

	/**
	 * Implementations should return an {@link AndroidInjector} for the concrete {@link
	 * DaggerApplication}. Typically, that injector is a {@link Component}.
	 */
	@Override
	protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
		return DaggerAppComponent.builder().create(this);
	}

	private void initializeLeakDetection() {
		if (BuildConfig.DEBUG) {
			LeakCanary.install(this);
		}
	}
}
