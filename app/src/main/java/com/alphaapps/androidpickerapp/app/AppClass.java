package com.alphaapps.androidpickerapp.app;

import android.content.res.Configuration;

import androidx.multidex.MultiDexApplication;

import com.alphaapps.androidpickerapp.dagger.component.AppComponent;
import com.alphaapps.androidpickerapp.dagger.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

/**
 * @Author: Muhammad Noamany
 * @Date: 1/16/2022
 * @Email: muhammadnoamany@gmail.com
 */
public class AppClass extends MultiDexApplication implements HasAndroidInjector {
    AppComponent appComponent;
    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    /**
     * Initializing of dagger
     */
    private void initDagger() {
        appComponent = DaggerAppComponent.builder()
                .context(this.getApplicationContext())
                .build();
        appComponent.inject(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}