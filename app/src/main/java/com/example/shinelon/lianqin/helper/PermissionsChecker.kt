package com.example.shinelon.lianqin.helper

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

/**
 * Created by Shinelon on 2018/2/6.
 */
class PermissionsChecker {
    val REQUEST_CODE = 1

    /**
     * 检测权限
     */
    fun checkPermission(activity: Activity, permissions: Array<String>){
        if(lackPermission(activity,permissions)){
            requestPermission(activity,permissions)
        }
    }

    fun requestPermission(activity: Activity,permissions: Array<String>){
        ActivityCompat.requestPermissions(activity,permissions,REQUEST_CODE)
    }

    fun lackPermission(activity: Activity,permissions: Array<String>): Boolean{
        permissions.forEach {
            if(ContextCompat.checkSelfPermission(activity,it) == PackageManager.PERMISSION_DENIED )
                return true
        }
        return false
    }


}