package com.alphaapps.androidpickerapp.dagger.component;

import android.content.Context;

import com.alphaapps.androidpickerapp.app.AppClass;
import com.alphaapps.androidpickerapp.dagger.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * @Author: Muhammad Noamany
 * @Date: 1/16/2022
 * @Email: muhammadnoamany@gmail.com
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class})
public interface AppComponent {
    void inject(AppClass appClass);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder context(Context context);
        AppComponent build();
    }

}
