package com.alphaapps.pickermodule.constants;

import android.Manifest;


/**
 * Contains the constants values used in the picker
 *
 * @Author: Muhammad Noamany
 * @Date: 1/13/2022
 * @Email: muhammadnoamany@gmail.com
 */
public class Constants {
    public static String[] GALLERY_PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};
    public static String[] CAMERA_PERMISSIONS = {Manifest.permission.CAMERA};
    public static final String ACTION_CAMERA_RESULT = "CameraPicker.result";
    public static final String ACTION_GALLERY_RESULT = "GalleryPicker.result";
    public static final String PERMISSIONS_KEY = "permissions";
    public static final String ACTION_PERMISSIONS_GRANTED = "GetPermissions.permissions_granted";
    public static final String ACTION_PERMISSIONS_DENIED = "GetPermissions.permissions_denied";

}
