package com.alphaapps.androidpickerapp.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.alphaapps.androidpickerapp.R;
import com.alphaapps.androidpickerapp.databinding.ActivityMainBinding;
import com.alphaapps.androidpickerapp.ui.base.BaseActivity;
import com.alphaapps.androidpickerapp.ui.picked_details.PickedDetailsActivity;
import com.alphaapps.pickermodule.Picker;
import com.alphaapps.pickermodule.callbacks.IPickerResult;
import com.alphaapps.pickermodule.data.ResultData;

/**
 * Created by Muhammad Noamany
 * Email: muhammadnoamany@gmail.com
 */
public class MainActivity extends BaseActivity<ActivityMainBinding> implements IPickerResult {
    private Picker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar(binding.appBarLayout.toolbar, R.drawable.ic_arrow_back, getString(R.string.home), false);
        picker = new Picker(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCameraResult(Bitmap bitmap) {
        PickedDetailsActivity.start(this, bitmap);
    }

    @Override
    public void onGalleryResult(ResultData resultObject) {
        PickedDetailsActivity.start(this, resultObject);
    }

    /**
     * On Pick from camera btn clicked
     */
    public void onCameraPickClicked(View view) {
        picker.startCameraPicker();
    }

    /**
     * On Pick from gallery btn clicked
     */
    public void onGalleryPickClicked(View view) {
        picker.startGalleryPicker();
    }

    /**
     * Inflate the settings gear icon on the toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_actions, menu);
        return true;
    }
}