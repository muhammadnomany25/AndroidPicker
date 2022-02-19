package com.alphaapps.pickermodule.callbacks;

import android.graphics.Bitmap;

/**
 * @usage: works as abstraction layer to handle the communication between the picker class and the Android component
 * which picker has been called from
 * @Author: Muhammad Noamany
 * @Date: 1/13/2022
 * @Email: muhammadnoamany@gmail.com
 */
public interface ICameraPickerResult {
    void onResult(Bitmap bitmap);
}
