package com.example.shinelon.lianqin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_brocast_view.*

/**
 * Created by Shinelon on 2018/2/6.
 */
class PublicBroadcastViewActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brocast_view)
        setSupportActionBar(activity_public_broadcast_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}