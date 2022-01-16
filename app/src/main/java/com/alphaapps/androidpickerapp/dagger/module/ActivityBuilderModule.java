package com.alphaapps.androidpickerapp.dagger.module;


import com.alphaapps.androidpickerapp.ui.home.MainActivity;
import com.alphaapps.androidpickerapp.ui.picked_details.PickedDetailsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Module of Injecting app activities for Dagger to use in DI
 *
 * @Author: Muhammad Noamany
 * @Date: 1/16/2022
 * @Email: muhammadnoamany@gmail.com
 */
@Module()
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract MainActivity MainActivity();

    @ContributesAndroidInjector
    abstract PickedDetailsActivity PickedDetailsActivity();

}

