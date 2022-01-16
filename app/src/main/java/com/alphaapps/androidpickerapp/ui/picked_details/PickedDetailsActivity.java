package com.alphaapps.androidpickerapp.ui.picked_details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alphaapps.androidpickerapp.R;
import com.alphaapps.androidpickerapp.databinding.ActivityPickedDetailsBinding;
import com.alphaapps.androidpickerapp.ui.base.BaseActivity;
import com.alphaapps.pickermodule.data.ResultData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.File;

/**
 * Created by Muhammad Noamany
 * Email: muhammadnoamany@gmail.com
 */
public class PickedDetailsActivity extends BaseActivity<ActivityPickedDetailsBinding> {
    private DetailsVM detailsVM;
    private SimpleExoPlayer player;
    private DefaultDataSourceFactory dataSourceFactory;
    private ResultData fileData;

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

    /**
     * get the intent extras
     */
    private void checkExtras() {
        Bitmap resultBitmap = getIntent().getExtras().getParcelable("resultBitmap");
        ResultData resultData = getIntent().getExtras().getParcelable("resultData");
        detailsVM.setPickedFileData(resultData);
        detailsVM.setTakenCameraPhoto(resultBitmap);

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
                fileData = resultData;
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
        binding.imageContainer.setVisibility(View.VISIBLE);
        binding.imageContainer.setImageBitmap(bitmap);
        binding.widthTV.setText(bitmap.getWidth() + "");
        binding.heightTV.setText(bitmap.getHeight() + "");
    }

    /**
     * Set observe data result
     */
    private void setResultData(ResultData resultData) {
        if (!resultData.isVideo()) {
            Glide.with(PickedDetailsActivity.this).load(new File(resultData.getFilePath())).apply(new RequestOptions().sizeMultiplier(0.5f)).into(binding.imageContainer);
            binding.imageContainer.setVisibility(View.VISIBLE);
        } else {
            binding.playerView.setVisibility(View.VISIBLE);
            initPlayerView();
            preparePlayer();
        }
        binding.setDataModel(resultData);
    }

    /**
     * Init of the ExoPlayer
     */
    private void initPlayerView() {
        player = new SimpleExoPlayer.Builder(PickedDetailsActivity.this).build();
        binding.playerView.setPlayer(player);
        dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, getString(R.string.app_name)));
    }

    /**
     * Prepare ExoPlayer
     */
    private void preparePlayer() {
        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(fileData.getFilePath()));
        player.prepare(videoSource);
        if (detailsVM.getPositionMilliSecondData().getValue() > 0)
            resumeExoPlayer();
        player.setPlayWhenReady(true);
    }

    /**
     * Resume ExoPlayer
     */
    private void resumeExoPlayer() {
        player.seekTo(detailsVM.getWindowIndexData().getValue(), detailsVM.getPositionMilliSecondData().getValue());
    }

    /**
     * release Exo Player on Activity pause
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (player != null && player.isPlaying()) {
            detailsVM.setWindowIndex(player.getCurrentWindowIndex());
            detailsVM.setPositionMilliSecond(Math.max(0, player.getContentPosition()));
            player.release();
        }

    }

}
