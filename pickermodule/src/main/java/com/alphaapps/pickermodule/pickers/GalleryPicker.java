package com.alphaapps.pickermodule.pickers;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alphaapps.pickermodule.constants.PickedFileType;
import com.alphaapps.pickermodule.data.ResultData;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.alphaapps.pickermodule.constants.Constants.ACTION_GALLERY_RESULT;
import static com.alphaapps.pickermodule.utils.MediaUtils.getFileSize;

/**
 * Camera picker activity that handles the picking from gallery for a video or an image
 *
 * @Author: Muhammad Noamany
 * @Date: 1/13/2022
 * @Email: muhammadnoamany@gmail.com
 */
public class GalleryPicker extends BasePicker {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openForGalleryPick();
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
        resultData.setFileSize(String.valueOf(getFileSize(returnCursor.getLong(sizeIndex))));
        File file = new File(selectedMediaUri.getPath());
        resultData.setFilePath(selectedMediaUri.getPath());
        if (selectedMediaUri.toString().contains("image")) {
            resultData.setType(PickedFileType.IMAGE.getValue());
        } else if (selectedMediaUri.toString().contains("video")) {
            resultData.setType(PickedFileType.VIDEO.getValue());
        }

        Log.e("path_file", getPathFromInputStreamUri(this, selectedMediaUri));
        sendResult(resultData);

    }

    public static String getPathFromInputStreamUri(Context context, Uri uri) {
        InputStream inputStream = null;
        String filePath = null;

        if (uri.getAuthority() != null) {
            try {
                inputStream = context.getContentResolver().openInputStream(uri);
                File photoFile = createTemporalFileFrom(context, inputStream);

                filePath = photoFile.getPath();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePath;
    }

    private static File createTemporalFileFrom(Context context, InputStream inputStream) throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];

            targetFile = createTemporalFile(context);
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return targetFile;
    }

    private static File createTemporalFile(Context context) {
        return new File(context.getCacheDir(), "tempPicture.jpg");
    }

    private String getFilePathFromContentUri(Uri selectedVideoUri,
                                             ContentResolver contentResolver) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};

        Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
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

}
