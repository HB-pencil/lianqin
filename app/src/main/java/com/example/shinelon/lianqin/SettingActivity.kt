package com.example.shinelon.lianqin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.shinelon.lianqin.fragment.SettingFragment
import kotlinx.android.synthetic.main.activity_container.*

/**
 * Created by Shinelon on 2017/12/2.
 */
class SettingActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        setSupportActionBar(toolbar_setting)
        val fm = fragmentManager
        var fragment = fm.findFragmentById(R.id.activity_container)
        if(fragment==null) fragment = SettingFragment.newInstance()
        fm.beginTransaction()
                .add(R.id.activity_container,fragment)
                .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}