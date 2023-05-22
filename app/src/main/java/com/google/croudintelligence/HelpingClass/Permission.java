package com.google.croudintelligence.HelpingClass;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class Permission {
    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 41;

    private boolean checkPermissionForReadExternalStorage(Context context) {
        int result = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private boolean requestPermissionForReadExternalStorage(Context context) throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
            return checkPermissionForReadExternalStorage(context);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean seekGalleryPermission(Context context) throws Exception {
        if (!checkPermissionForReadExternalStorage(context)) {
            return requestPermissionForReadExternalStorage(context);
        } else {
            return true;
        }
    }

    private boolean checkPermissionForCamera(Context context) {
        int result = context.checkSelfPermission(Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private boolean requestPermissionForCamera(Context context) throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
            return checkPermissionForReadExternalStorage(context);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean seekCameraPermission(Context context) throws Exception {
        if (!checkPermissionForCamera(context)) {
            return requestPermissionForCamera(context);
        } else {
            return true;
        }
    }
}
