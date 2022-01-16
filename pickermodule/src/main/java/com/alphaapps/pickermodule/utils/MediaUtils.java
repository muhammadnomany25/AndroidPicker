package com.alphaapps.pickermodule.utils;
/**
 * A helper class, its usage is to help mapping the URIs of the picked photos to a bitmap objects
 *
 * @Author: Muhammad Noamany
 * @Date: 1/13/2022
 * @Email: muhammadnoamany@gmail.com
 */


import android.content.Intent;
import android.graphics.Bitmap;

import java.text.DecimalFormat;

/**
 * Created by Muhammad Noamany
 * Email: muhammadnoamany@gmail.com
 */
public class MediaUtils {
    /**
     * get returned intent from camera as bitmap object
     */
    public static Bitmap returnBitmap(Intent data) {
        return (Bitmap) data.getExtras().get("data");
    }

}
