package com.example.shinelon.lianqin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.shinelon.lianqin.fragment.NoteDetailsFragment
import kotlinx.android.synthetic.main.activity_note_details_container.*

/**
 * Created by Shinelon on 2018/2/6.
 */
class NoteDetailsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details_container)

        setSupportActionBar(note_container_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var fragment = supportFragmentManager.findFragmentById(R.id.note_container)
        if(fragment==null){
            fragment = createFragment()
        }
        supportFragmentManager.beginTransaction().replace(R.id.note_container,fragment)
                .commit()
    }

    fun createFragment() = NoteDetailsFragment()
}