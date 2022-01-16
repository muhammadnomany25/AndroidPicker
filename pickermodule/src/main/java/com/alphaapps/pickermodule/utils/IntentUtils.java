package com.alphaapps.pickermodule.utils;

import android.content.Intent;

/**
 * @Author: Muhammad Noamany
 * @Date: 1/16/2022
 * @Email: muhammadnoamany@gmail.com
 */
public class IntentUtils {
    /**
     * Generate the intent of image picker from gallery
     */
    public static Intent getImagePickerIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        String[] mimetypes = {"image/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        return intent;
    }

    /**
     * Generate the intent of video picker from gallery
     */
    public static Intent getVideoPickerIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        String[] mimetypes = {"video/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        return intent;
    }
}
