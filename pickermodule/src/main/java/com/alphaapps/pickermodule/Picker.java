package com.alphaapps.pickermodule;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.alphaapps.pickermodule.callbacks.IGalleryPickType;
import com.alphaapps.pickermodule.callbacks.IPickerResult;
import com.alphaapps.pickermodule.constants.Constants;
import com.alphaapps.pickermodule.constants.PickType;
import com.alphaapps.pickermodule.constants.PickedFileType;
import com.alphaapps.pickermodule.data.ResultData;
import com.alphaapps.pickermodule.pickers.CameraPicker;
import com.alphaapps.pickermodule.pickers.GalleryPicker;
import com.alphaapps.pickermodule.pickers.gallery_pick_type_dialog.GalleryPickerTypeDialog;
import com.alphaapps.pickermodule.utils.PermissionUtil;
import com.google.gson.Gson;

import static com.alphaapps.pickermodule.constants.Constants.ACTION_CAMERA_RESULT;
import static com.alphaapps.pickermodule.constants.Constants.ACTION_GALLERY_ERROR_RESULT;
import static com.alphaapps.pickermodule.constants.Constants.ACTION_GALLERY_RESULT;
import static com.alphaapps.pickermodule.constants.Constants.ACTION_PERMISSIONS_DENIED;
import static com.alphaapps.pickermodule.constants.Constants.ACTION_PERMISSIONS_GRANTED;

/**
 * @usage: handles the communication between the pickers {picking and its results} and Android Components which need to pick
 * from camera or gallery
 * @Author: Muhammad Noamany
 * @Date: 1/13/2022
 * @Email: muhammadnoamany@gmail.com
 */
public class Picker extends BroadcastReceiver implements IGalleryPickType {
    private Context context;
    private IPickerResult iPickerResult;
    private int pickType;

    /**
     * Constructor to make use of initializing class variables
     *
     * @param context
     */
    public Picker(Context context) {
        this.context = context;
        this.iPickerResult = (IPickerResult) context;
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
                iPickerResult.onGalleryResult(resultData);
                break;
            }

            // case camera take photo  result
            case ACTION_CAMERA_RESULT: {
                Log.e("pickResult", "ACTION_CAMERA_RESULT");
                Bitmap resultData = intent.getExtras().getParcelable("result");
                iPickerResult.onCameraResult(resultData);
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
            startCameraPicker();
        else if (pickType == PickType.GALLERY.getValue())
            startGalleryPicker();
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
    public void startGalleryPicker() {
        this.pickType = PickType.GALLERY.getValue();
        initReceiver();
        checkForGalleryPick();
    }

    /**
     * start camera picker process
     */
    public void startCameraPicker() {
        this.pickType = PickType.CAMERA.getValue();
        initReceiver();
        checkForCameraPick();
    }

    /**
     * Check if permissions is granted then user able to pick from gallery
     */
    private void checkForGalleryPick() {
        if (PermissionUtil.isPermissionGranted(context, Constants.GALLERY_PERMISSIONS))
            handleGalleryPicker();
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
     */
    private void handleGalleryPicker() {
        new GalleryPickerTypeDialog(context, this).show();
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
    @Override
    public void onImagePick() {
        Intent intent = new Intent(context, GalleryPicker.class);
        intent.putExtra(Constants.GALLERY_PICK_TYPE_INIT, PickedFileType.IMAGE.getValue());
        context.startActivity(intent);
    }

    /**
     * On user chooses gallery video picker
     */
    @Override
    public void onVideoPick() {
        Intent intent = new Intent(context, GalleryPicker.class);
        intent.putExtra(Constants.GALLERY_PICK_TYPE_INIT, PickedFileType.VIDEO.getValue());
        context.startActivity(intent);
    }
}
