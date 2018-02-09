package com.example.shinelon.lianqin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_student_details.*

/**
 * Created by Shinelon on 2018/2/8.
 */
class StudentDetailsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)
        setSupportActionBar(activity_student_details_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}