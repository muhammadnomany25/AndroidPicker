package com.alphaapps.pickermodule.callbacks;

import android.graphics.Bitmap;

import com.alphaapps.pickermodule.data.ResultData;

/**
 * @usage: works as abstraction layer to handle the communication between the picker class and the Android component
 * which picker has beed called from
 * @Author: Muhammad Noamany
 * @Date: 1/13/2022
 * @Email: muhammadnoamany@gmail.com
 */
public interface IPickerResult {
    void onCameraResult(Bitmap bitmap);

    void onGalleryResult(ResultData resultObject);
}
