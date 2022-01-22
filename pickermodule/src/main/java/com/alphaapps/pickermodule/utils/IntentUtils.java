package com.alphaapps.pickermodule.utils;

import android.content.Intent;
import android.provider.MediaStore;

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
        return new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    /**
     * Generate the intent of video picker from gallery
     */
    public static Intent getVideoPickerIntent() {
        return new Intent(Intent.ACTION_PICK,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
    }
}
