package com.alphaapps.pickermodule.pickers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The super class for pickers
 * have most common and needed methods for picker to work
 *
 * @Author: Muhammad Noamany
 * @Date: 1/13/2022
 * @Email: muhammadnoamany@gmail.com
 */
abstract class BasePicker extends AppCompatActivity {
    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLauncher();
    }

    /**
     * Initialization of the result launcher that will open the video and images selection intent
     */
    private void initLauncher() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        Intent data = result.getData();
                        onResult(data);
                    } else finish();
                });
    }


    /**
     * Send broadcast with the selected data {will be received by Picker Class}
     */
    abstract void onResult(Intent intentData);

    /**
     * Open camera picker intent
     */
    protected void openForCameraPick() {
        initLauncher();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        resultLauncher.launch(takePictureIntent);
    }

    /**
     * Open gallery picker intent
     */
    protected void openForGalleryPick() {
        initLauncher();
        String[] types = {"image/*", "video/*"};
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, types);
        resultLauncher.launch(intent);
    }
}