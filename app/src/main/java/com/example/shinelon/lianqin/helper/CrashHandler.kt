package com.example.shinelon.lianqin.helper

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Process
import android.util.Log
import com.example.shinelon.lianqin.MyApplication
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Shinelon on 2018/2/23.
 */
object CrashHandler: Thread.UncaughtExceptionHandler {
    val pPath = Environment.getExternalStorageDirectory().path+"/CrashLog"
    val default = Thread.getDefaultUncaughtExceptionHandler()
    val file = File(pPath)
    var context: Context? = null

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
        if (!file.exists()) file.mkdirs()
        Log.e("CrashHandler","执行！")
    }

    fun setC(c: Application){
        context = c
    }

    override fun uncaughtException(p0: Thread?, p1: Throwable?) {
        try {
             if(Environment.getExternalStorageState()!=Environment.MEDIA_MOUNTED){
                 return
             }else{
                 val time = System.currentTimeMillis()
                 val f = File(file,"crashlog$time.trace")
                 if (!f.exists()) f.createNewFile()
                 val t = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(time))
                 val pw = PrintWriter(BufferedWriter(FileWriter(f)))
                 pw.print(t)
                 dumptoPhone(pw)
                 pw.println()
                 p1?.printStackTrace(pw)
                 pw.close()
             }
        }catch (e: Exception){
             e.printStackTrace()
        }
        default?.uncaughtException(p0, p1)
        Process.killProcess(Process.myPid())
    }

    fun dumptoPhone(pw: PrintWriter){
        val pm = context?.packageManager
        val pi =pm?.getPackageInfo(context?.packageName,PackageManager.GET_ACTIVITIES)
        pw.print("AppVersion:")
        pw.print(pi?.versionName)
        pw.print("_")
        pw.println(pi?.versionCode)

        pw.print("OS Version：")
        pw.print(Build.VERSION.RELEASE)
        pw.print("_")
        pw.println(Build.VERSION.SDK_INT)

        pw.print("手机制造商：")
        pw.println(Build.MANUFACTURER)

        pw.print("手机型号：")
        pw.println(Build.MODEL)

        pw.print("CPU:")
        pw.println(Build.CPU_ABI)
    }
}