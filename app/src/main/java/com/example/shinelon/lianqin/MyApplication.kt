package com.example.shinelon.lianqin

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatDelegate
import com.example.shinelon.lianqin.helper.CrashHandler
import com.example.shinelon.lianqin.helper.NotesSQLiteHelper

/**
 * Created by HB on 2017/12/2.夜间模式设置
 */
class MyApplication: Application() {
    var db: SQLiteDatabase? = null
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        val dh = NotesSQLiteHelper(this)
        db = dh.writableDatabase
        CrashHandler.setC(this)
    }
}