package com.alphaapps.androidpickerapp.ui.picked_details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alphaapps.androidpickerapp.R;
import com.alphaapps.androidpickerapp.databinding.ActivityPickedDetailsBinding;
import com.alphaapps.androidpickerapp.ui.base.BaseActivity;
import com.alphaapps.pickermodule.data.ResultData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 * Created by Muhammad Noamany
 * Email: muhammadnoamany@gmail.com
 */
public class PickedDetailsActivity extends BaseActivity<ActivityPickedDetailsBinding> {
    private DetailsVM detailsVM;

    /**
     * start activity helper method
     */
    public static void start(Context context, Bitmap resultBitmap) {
        Intent intent = new Intent(context, PickedDetailsActivity.class);
        intent.putExtra("resultBitmap", resultBitmap);
        context.startActivity(intent);
    }

    /**
     * start activity helper method
     */
    public static void start(Context context, ResultData resultData) {
        Intent intent = new Intent(context, PickedDetailsActivity.class);
        intent.putExtra("resultData", resultData);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar(binding.appBarLayout.toolbar, R.drawable.ic_arrow_back, getString(R.string.details), true);
        setViewModel();
        checkExtras();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picked_details;
    }

    /**
     * init the viewModel
     */
    private void setViewModel() {
        detailsVM = new ViewModelProvider(this).get(DetailsVM.class);
        observeData();
    }

    /**
     * Observe data from view model
     */
    private void observeData() {
        detailsVM.getPickedFileData().observe(this, resultData -> {
            if (resultData != null) {
                setResultData(resultData);
            }
        });

        detailsVM.getTakenCameraPhoto().observe(this, bitmap -> {
            if (bitmap != null) {
                setResultData(bitmap);
            }
        });
    }

    /**
     * Set observe data result
     */
    private void setResultData(Bitmap bitmap) {
        binding.imageContainer.setImageBitmap(bitmap);
        binding.widthTV.setText(bitmap.getWidth() + "");
        binding.heightTV.setText(bitmap.getHeight() + "");
    }

    /**
     * Set observe data result
     */
    private void setResultData(ResultData resultData) {
        if (!resultData.isVideo())
            Glide.with(PickedDetailsActivity.this).load(new File(resultData.getFilePath())).apply(new RequestOptions().sizeMultiplier(0.5f)).into(binding.imageContainer);
        binding.setDataModel(resultData);
    }

    /**
     * get the intent extras
     */
    private void checkExtras() {
        Bitmap resultBitmap = getIntent().getExtras().getParcelable("resultBitmap");
        ResultData resultData = getIntent().getExtras().getParcelable("resultData");
        detailsVM.setPickedFileData(resultData);
        detailsVM.setTakenCameraPhoto(resultBitmap);

    }
}
