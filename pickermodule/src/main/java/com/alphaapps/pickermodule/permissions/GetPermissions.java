package com.alphaapps.pickermodule.permissions;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static com.alphaapps.pickermodule.constants.Constants.ACTION_PERMISSIONS_DENIED;
import static com.alphaapps.pickermodule.constants.Constants.ACTION_PERMISSIONS_GRANTED;
import static com.alphaapps.pickermodule.constants.Constants.PERMISSIONS_KEY;

/**
 * Get Required permissions for camera or gallery picker
 *
 * @Author: Muhammad Noamany
 * @Date: 1/13/2022
 * @Email: muhammadnoamany@gmail.com
 */
public class GetPermissions extends AppCompatActivity {

    private int permissionRequestCode = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
    }

    /**
     * Pop the system request permissions dialog
     */
    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                getIntent().getStringArrayExtra(PERMISSIONS_KEY),
                permissionRequestCode
        );
    }

    /**
     * Handle the permissions result
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permissionRequestCode) {
            sendResult(grantResults);
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Send broadcast with the permissions result status {will be received by Picker Class}
     */
    private void sendResult(int[] grantResults) {
        if ((grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            sendBroadcast(new Intent(ACTION_PERMISSIONS_GRANTED));
        } else {
            sendBroadcast(new Intent(ACTION_PERMISSIONS_DENIED));
        }
        finish();
    }
}
