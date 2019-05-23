package com.example.mytrainingapp.managers

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mytrainingapp.common.PERMISSIONS_REQUEST_CODE

object RuntimePermissionsManager {

    private val messageLiveData = MutableLiveData<String>()
    private val hasPermissionLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun checkPermissions(activity: Activity): LiveData<Boolean> {
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                messageLiveData.value =
                    "Your location is needed"
            } else {
                requestPermissions(activity)
            }
        } else {
            hasPermissionLiveData.value = true
        }

        return hasPermissionLiveData
    }

    fun requestPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSIONS_REQUEST_CODE
        )
    }

    fun getMessageLiveData(): LiveData<String> = messageLiveData
}