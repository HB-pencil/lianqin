package com.example.shinelon.lianqin.fragment

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.util.Log
import com.bumptech.glide.disklrucache.DiskLruCache
import com.example.shinelon.lianqin.R
import java.io.File
import java.security.MessageDigest
import kotlin.experimental.and
import kotlin.properties.Delegates


/**
 * Created by HB on 2017/12/2.
 */
class SettingFragment:PreferenceFragment(),SharedPreferences.OnSharedPreferenceChangeListener{
    private var cachePref: Preference? = null
    private var cache: DiskLruCache by Delegates.notNull()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.setting_pre)
        updateCache()

        val pre = PreferenceManager.getDefaultSharedPreferences(activity)
        findPreference("bind_phone").summary = pre.getString("bind_phone","点击绑定您的手机号码")
        findPreference("bind_email").summary = pre.getString("bind_email","点击绑定您的手机邮箱")
        findPreference("about").summary = "version-1.0"

        findPreference("clear").setOnPreferenceClickListener {
            clearCache()
        }

        findPreference("about").setOnPreferenceClickListener{
            val dialog = AlertDialog.Builder(activity)
                    .setTitle("关于本软件")
                    .setMessage("本软件是广州中医药大学医学信息工程学院互联网产品设计大赛参赛作品，作者为“年轻”队伍")
                    .setPositiveButton("好的我知道了"){
                        dialogInterface, i ->
                    }
                    .create()
                    .show()
            false
        }

        findPreference("suggest").setOnPreferenceClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SENDTO
            intent.type = "text/plain"
            //val array = arrayOf("hardblack@aliyun.com")
            //intent.putExtra(Intent.EXTRA_EMAIL,array)
            intent.data= Uri.parse("mailto:hardblack@aliyun.com")
            intent.putExtra(Intent.EXTRA_SUBJECT,"意见反馈")
            if(intent.resolveActivity(activity.packageManager)!=null) startActivity(intent)
            false
        }

        val file = getDiskCach()
        cache = DiskLruCache.open(file,1,1,1*1024*1024*10)//第三个参数一个key对应多少文件
        //待补充,edit(key)后newOutputStream写入之后commit或者abort，最后flush
    }

    fun getDiskCach(): File{
        var cachPath = ""
        if(!Environment.isExternalStorageRemovable()||Environment.MEDIA_MOUNTED==Environment.getExternalStorageState()){
            cachPath = activity.externalCacheDir.path
        }else{
            cachPath = activity.cacheDir.path
        }
        val file = File(cachPath)
        if (!file.exists()) file.mkdirs()
        return file
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        Log.e("preferencesChange","执行")
        when(key){
            "bind_phone"-> findPreference(key).summary = sharedPreferences?.getString(key,getString(R.string.bind_phone_summary_setting))
            "bind_email"-> findPreference(key).summary = sharedPreferences?.getString(key,getString(R.string.bind_email_summary_setting))

        }
    }


    companion object {
        fun newInstance(): SettingFragment{
            val fragment = SettingFragment()
            return fragment
        }
    }

    fun clearCache():Boolean{
        activity.externalCacheDir
        cache.delete()
        Snackbar.make(view,"清除缓存成功",Snackbar.LENGTH_SHORT).show()
        return true
    }
    fun updateCache(){
        val size = cache.size()/1024/1024F
        activity.externalCacheDirs
        cachePref = findPreference("clear")
        cachePref?.summary = "缓存大小为${size}M"
    }

    fun urlToMd5Key(url: String): String{
        val message = MessageDigest.getInstance("MD5")
        message.update(url.toByte())
        val key = byteToHexString(message.digest())
        return key
    }

    fun byteToHexString(orgin: ByteArray): String{
        val stringBuilder = StringBuilder()
        orgin.forEach {
            val str = Integer.toHexString(it.toInt() and 0xff)
            if (str.length==1) stringBuilder.append(0)
            stringBuilder.append(str)
        }
        return stringBuilder.toString()
    }
}