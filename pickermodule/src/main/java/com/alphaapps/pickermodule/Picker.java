package com.alphaapps.pickermodule;


import static com.alphaapps.pickermodule.constants.Constants.ACTION_CAMERA_RESULT;
import static com.alphaapps.pickermodule.constants.Constants.ACTION_GALLERY_ERROR_RESULT;
import static com.alphaapps.pickermodule.constants.Constants.ACTION_GALLERY_RESULT;
import static com.alphaapps.pickermodule.constants.Constants.ACTION_PERMISSIONS_DENIED;
import static com.alphaapps.pickermodule.constants.Constants.ACTION_PERMISSIONS_GRANTED;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.alphaapps.pickermodule.callbacks.ICameraPickerResult;
import com.alphaapps.pickermodule.callbacks.IGalleryPickType;
import com.alphaapps.pickermodule.callbacks.IGalleryPickerResult;
import com.alphaapps.pickermodule.constants.Constants;
import com.alphaapps.pickermodule.constants.PickType;
import com.alphaapps.pickermodule.constants.PickedFileType;
import com.alphaapps.pickermodule.data.ResultData;
import com.alphaapps.pickermodule.pickers.CameraPicker;
import com.alphaapps.pickermodule.pickers.GalleryPicker;
import com.alphaapps.pickermodule.utils.PermissionUtil;
import com.google.gson.Gson;

/**
 * @usage: handles the communication between the pickers {picking and its results} and Android Components which need to pick
 * from camera or gallery
 * @Author: Muhammad Noamany
 * @Date: 1/13/2022
 * @Email: muhammadnoamany@gmail.com
 */
public class Picker extends BroadcastReceiver implements IGalleryPickType {
    private Context context;
    //    private IPickerResult iPickerResult;
    private IGalleryPickerResult iGalleryPickerResult;
    private ICameraPickerResult iCameraPickerResult;
    private int pickType;

    /**
     * Constructor to make use of initializing class variables
     *
     * @param context
     */
    public Picker(Context context) {
        this.context = context;
    }

    /**
     * Initialize a broadcast receiver to listen for permissions grants or denied
     */
    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_PERMISSIONS_GRANTED);
        intentFilter.addAction(ACTION_PERMISSIONS_DENIED);
        intentFilter.addAction(ACTION_CAMERA_RESULT);
        intentFilter.addAction(ACTION_GALLERY_RESULT);
        intentFilter.addAction(ACTION_GALLERY_ERROR_RESULT);
        context.registerReceiver(this, intentFilter);
    }

    /**
     * The OnReceive of the permissions broadcast receivers
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            // case certain permission or permissions are being granted
            case ACTION_PERMISSIONS_GRANTED: {
                context.unregisterReceiver(this);
                onPermissionsGranted();
                break;
            }

            // case certain permission or permissions are being denied
            case ACTION_PERMISSIONS_DENIED: {
                context.unregisterReceiver(this);
                onPermissionsDenied();
                break;
            }

            // case gallery pick photo or video result
            case ACTION_GALLERY_RESULT: {
                Log.e("pickResult", "ACTION_GALLERY_RESULT");
                ResultData resultData = new Gson().fromJson(intent.getExtras().getString("result"), ResultData.class);
                iGalleryPickerResult.onResult(resultData);
                break;
            }

            // case camera take photo  result
            case ACTION_CAMERA_RESULT: {
                Log.e("pickResult", "ACTION_CAMERA_RESULT");
                Bitmap resultData = intent.getExtras().getParcelable("result");
                iCameraPickerResult.onResult(resultData);
                break;
            }
            // case camera take photo  result
            case ACTION_GALLERY_ERROR_RESULT: {
                showErrorToast(context, intent);
                break;
            }
        }
    }

    /**
     * Show the error message returned from intent
     */
    private void showErrorToast(Context context, Intent intent) {
        Log.e("pickResult", "ACTION_GALLERY_ERROR_RESULT");
        String resultError = intent.getExtras().getString("result");
        Toast.makeText(context, resultError, Toast.LENGTH_SHORT).show();
    }

    /**
     * Being triggered when permissions are being granted
     */
    private void onPermissionsGranted() {
        if (pickType == PickType.CAMERA.getValue())
            startCameraPicker(iCameraPickerResult);
        else if (pickType == PickType.GALLERY_IMAGE.getValue())
            startGalleryImagePicker(iGalleryPickerResult);
        else if (pickType == PickType.GALLERY_VIDEO.getValue())
            startGalleryVideoPicker(iGalleryPickerResult);
    }

    /**
     * Being triggered when permissions are being denied
     */
    private void onPermissionsDenied() {
        Log.e("Permissions", "Denied");
        Toast.makeText(context, R.string.must_accept_permissions, Toast.LENGTH_LONG).show();
    }

    /**
     * start gallery picker process
     */
    public void startGalleryVideoPicker(IGalleryPickerResult iGalleryPickerResult) {
        this.iGalleryPickerResult = iGalleryPickerResult;
        this.pickType = PickType.GALLERY_VIDEO.getValue();
        initReceiver();
        checkForGalleryPick(false);
    }

    /**
     * start gallery picker process
     */
    public void startGalleryImagePicker(IGalleryPickerResult iGalleryPickerResult) {
        this.iGalleryPickerResult = iGalleryPickerResult;
        this.pickType = PickType.GALLERY_IMAGE.getValue();
        initReceiver();
        checkForGalleryPick(true);
    }

    /**
     * start camera picker process
     */
    public void startCameraPicker(ICameraPickerResult iCameraPickerResult) {
        this.iCameraPickerResult = iCameraPickerResult;
        this.pickType = PickType.CAMERA.getValue();
        initReceiver();
        checkForCameraPick();
    }

    /**
     * Check if permissions is granted then user able to pick from gallery
     */
    private void checkForGalleryPick(boolean isImagePicker) {
        if (PermissionUtil.isPermissionGranted(context, Constants.GALLERY_PERMISSIONS))
            handleGalleryPicker(isImagePicker);
        else {
            PermissionUtil.askForPermissions(context, Constants.GALLERY_PERMISSIONS);
        }
    }

    /**
     * Check if permissions is granted then user able to pick from Camera
     */
    private void checkForCameraPick() {
        if (PermissionUtil.isPermissionGranted(context, Constants.CAMERA_PERMISSIONS))
            handleCameraPicker();
        else {
            PermissionUtil.askForPermissions(context, Constants.CAMERA_PERMISSIONS);
        }
    }

    /**
     * Start Gallery Picker Sheet dialog pick
     *
     * @param isImagePicker
     */
    private void handleGalleryPicker(boolean isImagePicker) {
        if (isImagePicker) onImagePick();
        else onVideoPick();
    }

    /**
     * Start Camera Picker
     */
    private void handleCameraPicker() {
        context.startActivity(new Intent(context, CameraPicker.class));
    }

    /**
     * On user chooses gallery image picker
     */

    public void onImagePick() {
        Intent intent = new Intent(context, GalleryPicker.class);
        intent.putExtra(Constants.GALLERY_PICK_TYPE_INIT, PickedFileType.IMAGE.getValue());
        context.startActivity(intent);
    }

    /**
     * On user chooses gallery video picker
     */

    public void onVideoPick() {
        Intent intent = new Intent(context, GalleryPicker.class);
        intent.putExtra(Constants.GALLERY_PICK_TYPE_INIT, PickedFileType.VIDEO.getValue());
        context.startActivity(intent);
    }
}
