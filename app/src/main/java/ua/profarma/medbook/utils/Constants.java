package ua.profarma.medbook.utils;

import android.Manifest;

public class Constants {
    //permissions
    public static final String[]    LOCATION_PERMISSIONS            = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    public static final String      CAMERA_PERMISSION               = Manifest.permission.CAMERA;
    public static final String      READ_PHONE_STATE_PERMISSION     = Manifest.permission.READ_PHONE_STATE;
    public static final String[]    STORAGE_PERMISSIONS             = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final String[]    STORAGE_AND_CAMERA_PERMISSIONS  = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    public static final int CAMERA_PERMISSION_REQUEST_CODE = 2012;
    public static final int READ_PHONE_STATE_PERMISSION_REQUEST_CODE = 2013;

}
