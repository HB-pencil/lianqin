package com.example.shinelon.lianqin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_manual_check.*

/**
 * Created by Shinelon on 2018/1/31.
 */
class ManualActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_check)
        setSupportActionBar(manual_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        manual_check_confirm.setOnClickListener {
            Toast.makeText(this,"点击！",Toast.LENGTH_SHORT).show()
        }
    }
}