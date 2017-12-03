package com.example.shinelon.lianqin.fragment

import android.app.Fragment
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceFragment
import com.example.shinelon.lianqin.R


/**
 * Created by Shinelon on 2017/12/2.
 */
class SettingFragment:PreferenceFragment(),SharedPreferences.OnSharedPreferenceChangeListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.setting_pre)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

    }

    companion object {
        fun newInstance(): SettingFragment{
            val fragment = SettingFragment()
            return fragment
        }
    }

}