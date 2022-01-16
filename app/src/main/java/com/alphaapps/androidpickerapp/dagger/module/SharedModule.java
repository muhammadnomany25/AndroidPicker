package com.alphaapps.androidpickerapp.dagger.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.alphaapps.androidpickerapp.data.shared_prefs.UserSaver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module of Shared Prefs for Dagger to use in DI
 *
 * @Author: Muhammad Noamany
 * @Date: 1/16/2022
 * @Email: muhammadnoamany@gmail.com
 */
@Module
public class SharedModule {


    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context ctx) {
        return ctx.getSharedPreferences(ctx.getApplicationInfo().name, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    SharedPreferences.Editor provideSharedPreferencesEditor(SharedPreferences preferences) {
        return preferences.edit();
    }

    @Provides
    @Singleton
    UserSaver provideUserSaver(SharedPreferences preferences, SharedPreferences.Editor editor) {
        return new UserSaver(preferences, editor);
    }
}
