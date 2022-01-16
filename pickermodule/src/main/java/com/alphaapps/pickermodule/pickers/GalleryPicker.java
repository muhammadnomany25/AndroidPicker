package com.alphaapps.pickermodule.pickers;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alphaapps.pickermodule.constants.Constants;
import com.alphaapps.pickermodule.constants.PickedFileType;
import com.alphaapps.pickermodule.data.ResultData;
import com.google.gson.Gson;

import static com.alphaapps.pickermodule.constants.Constants.ACTION_GALLERY_RESULT;
import static com.alphaapps.pickermodule.utils.FileUtils.getFileSize;
import static com.alphaapps.pickermodule.utils.FileUtils.getPath;

/**
 * Camera picker activity that handles the picking from gallery for a video or an image
 *
 * @Author: Muhammad Noamany
 * @Date: 1/13/2022
 * @Email: muhammadnoamany@gmail.com
 */
public class GalleryPicker extends BasePicker {
    private int pickType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkExtras();
    }

    /**
     * Check pick type before start picking
     */
    private void checkExtras() {
        pickType = getIntent().getIntExtra(Constants.GALLERY_PICK_TYPE_INIT, 0);
        if (isImagePick())
            openGalleryForPickImage();
        else openGalleryForPickVideo();
    }

    @Override
    void onResult(Intent intentData) {
        handleResult(intentData.getData());
    }

    /**
     * Handle selection result for both images and videos
     */
    private void handleResult(Uri selectedMediaUri) {
        ResultData resultData = new ResultData();
        // https://developer.android.com/training/secure-file-sharing/retrieve-info

        Cursor returnCursor =
                getContentResolver().query(selectedMediaUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         * move to the first row in the Cursor, get the data,
         * and display it.
         */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        resultData.setName(returnCursor.getString(nameIndex));
        resultData.setFileSize(getFileSize(returnCursor.getLong(sizeIndex)));
        resultData.setVideo(!isImagePick());
        resultData.setFilePath(getPath(this, selectedMediaUri));
        sendResult(resultData);
    }

    /**
     * Send broadcast with the selected data {will be received by Picker Class}
     */
    private void sendResult(ResultData resultData) {
        Intent intent = new Intent(ACTION_GALLERY_RESULT);
        intent.putExtra("result", new Gson().toJson(resultData));
        sendBroadcast(intent);
        finish();
    }

    /**
     * check if the picker is an image picker
     */
    private boolean isImagePick() {
        return pickType == PickedFileType.IMAGE.getValue();
    }

}
