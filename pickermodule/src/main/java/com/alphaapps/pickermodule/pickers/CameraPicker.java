package com.alphaapps.pickermodule.pickers;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alphaapps.pickermodule.utils.MediaUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static com.alphaapps.pickermodule.constants.Constants.ACTION_CAMERA_RESULT;

/**
 * Camera picker activity that handles the picking from camera
 *
 * @Author: Muhammad Noamany
 * @Date: 1/13/2022
 * @Email: muhammadnoamany@gmail.com
 */
public class CameraPicker extends BasePicker {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openForCameraPick();
    }

    @Override
    void onResult(Intent intentData) {
        Bitmap bitmap = MediaUtils.returnBitmap(intentData);
        if (bitmap == null) return;
        compressAndSendResult(bitmap);
    }

    /**
     * Compress the picked image so as In low memory mobiles the app may get Allocation memory exception
     */
    private void compressAndSendResult(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
        sendResultIntent(decoded);
    }

    /**
     * Send broadcast with the selected data {will be received by Picker Class}
     */
    private void sendResultIntent(Bitmap resource) {
        Intent intent = new Intent(ACTION_CAMERA_RESULT);
        intent.putExtra("result", resource);
        sendBroadcast(intent);
        finish();
    }

}