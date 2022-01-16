package com.alphaapps.androidpickerapp.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alphaapps.androidpickerapp.R;
import com.alphaapps.androidpickerapp.databinding.ActivityMainBinding;
import com.alphaapps.androidpickerapp.ui.picked_details.PickedDetailsActivity;
import com.alphaapps.pickermodule.Picker;
import com.alphaapps.pickermodule.callbacks.IPickerResult;
import com.alphaapps.pickermodule.data.ResultData;

public class MainActivity extends AppCompatActivity implements IPickerResult {
    private ActivityMainBinding binding;
    private Picker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        picker = new Picker(this);
    }

    @Override
    public void onCameraResult(Bitmap bitmap) {
        PickedDetailsActivity.start(this, bitmap);
    }

    @Override
    public void onGalleryResult(ResultData resultObject) {
        PickedDetailsActivity.start(this, resultObject);
    }

    public void onCameraPickClicked(View view) {
        picker.startCameraPicker();
    }

    public void onGalleryPickClicked(View view) {
        picker.startGalleryPicker();
    }

}