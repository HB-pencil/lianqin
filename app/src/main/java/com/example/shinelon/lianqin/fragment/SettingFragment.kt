package com.example.shinelon.lianqin.fragment

import android.app.Fragment
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.util.Log
import com.example.shinelon.lianqin.R


/**
 * Created by Shinelon on 2017/12/2.
 */
class SettingFragment:PreferenceFragment(),SharedPreferences.OnSharedPreferenceChangeListener{
    private var cachePref: Preference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.setting_pre)
        updateCache()

        val pre = PreferenceManager.getDefaultSharedPreferences(activity)
        findPreference("bind_phone").summary = pre.getString("bind_phone","点击绑定您的手机号码")
        findPreference("bind_email").summary = pre.getString("bind_email","点击绑定您的手机邮箱")

        findPreference("clear").setOnPreferenceClickListener {
            clearCache()
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
        Snackbar.make(view,"清除缓存成功",Snackbar.LENGTH_SHORT).show()
        return true
    }
    fun updateCache(){
        val size = 0
        activity.externalCacheDirs
        cachePref = findPreference("clear")
        cachePref?.summary = "缓存大小为${size}M"
    }
}