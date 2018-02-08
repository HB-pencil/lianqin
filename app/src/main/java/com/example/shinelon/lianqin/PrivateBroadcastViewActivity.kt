package com.example.shinelon.lianqin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_brocast_view.*
import kotlinx.android.synthetic.main.activity_manual_check.*
import kotlinx.android.synthetic.main.activity_private_messages.*

/**
 * Created by Shinelon on 2018/1/31.
 */
class PrivateBroadcastViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_messages)
        setSupportActionBar(activity_private_broadcast_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}