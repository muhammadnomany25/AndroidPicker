package com.alphaapps.pickermodule.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.text.DecimalFormat;

/**
 * @usage: Helper class for Files features
 * @Author: Muhammad Noamany
 * @Date: 1/16/2022
 * @Email: muhammadnoamany@gmail.com
 */
public class FileUtils {
    private static final String LOG_TAG = FileUtils.class.getSimpleName();
    private static final String[] SIZE_UNITS = new String[]{"B", "KB", "MB", "GB", "TB"};   // File size units
    private static final String SIZE_FORMAT = "#,##0.#";   // th decimal format of file sizes
    private static final String ZERO_FILE_SIZE = "0";   //  zero file size value

    /**
     * Return Readable size of file
     */
    public static String getFileSize(long size) {
        if (size <= 0)
            return ZERO_FILE_SIZE;

        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat(SIZE_FORMAT).format(size / Math.pow(1024, digitGroups)) + " " + SIZE_UNITS[digitGroups];
    }

    /**
     * Method for return file path of Gallery Picked Image
     *
     * @param context
     * @param uri
     * @return path of the selected image file from gallery
     */
    public static String getImagePath(Context context, Uri uri) {
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = context.getContentResolver().query(uri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            return picturePath;
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * Method for return file path of Gallery Picked Video
     *
     * @param context
     * @param uri
     * @return path of the selected video file from gallery
     */
    public static String getVideoPath(Context context, Uri uri) {
        try {
            String[] filePathColumn = {MediaStore.Video.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String videoPath = cursor.getString(columnIndex);
            cursor.close();
            return videoPath;
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, e.getLocalizedMessage());
            return null;
        }
    }
}
