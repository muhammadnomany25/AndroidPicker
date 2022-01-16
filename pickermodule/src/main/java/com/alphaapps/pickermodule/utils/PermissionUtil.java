package com.alphaapps.pickermodule.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.alphaapps.pickermodule.constants.Constants;
import com.alphaapps.pickermodule.permissions.GetPermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * @usage: handle the checking of granted permission and to make it easier to handle asking for not granted permissions
 * @Author: Muhammad Noamany
 * @Date: 1/13/2022
 * @Email: muhammadnoamany@gmail.com
 */
public class PermissionUtil {
    /**
     * Check if certain permission is granted
     */
    public static Boolean isPermissionGranted(Context context, String permission) {
        int selfPermission = ContextCompat.checkSelfPermission(context, permission);
        return selfPermission == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Check if certain permissions are granted or not.
     */
    public static Boolean isPermissionGranted(Context context, String[] permissions) {
        List<String> grantedOnes = new ArrayList<>();
        for (String permission : permissions) {
            if (isPermissionGranted(context, permission))
                grantedOnes.add(permission);
        }
        return grantedOnes.size() == permissions.length;
    }

    /**
     * Ask user to grant certain permission
     */
    public static void askForPermission(Context context, String permission) {
        Intent intent = new Intent(context, GetPermissions.class);
        String[] permissionsToCheck = {permission};
        intent.putExtra(Constants.PERMISSIONS_KEY, permissionsToCheck);
        context.startActivity(intent);
    }

    /**
     * Ask user to grant certain permissions
     */
    public static void askForPermissions(Context context, String[] permissions) {
        Intent intent = new Intent(context, GetPermissions.class);
        intent.putExtra(Constants.PERMISSIONS_KEY, permissions);
        context.startActivity(intent);
    }
}
