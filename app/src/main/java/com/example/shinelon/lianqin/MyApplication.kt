package com.example.shinelon.lianqin

import android.app.Application
import android.support.v7.app.AppCompatDelegate

/**
 * Created by Shinelon on 2017/12/2.
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
}