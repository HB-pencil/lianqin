package com.example.shinelon.lianqin.presenter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.MenuItem
import com.example.shinelon.lianqin.MainActivity
import com.example.shinelon.lianqin.R
import com.example.shinelon.lianqin.SettingActivity

import com.example.shinelon.lianqin.view.MainView

/**
 * Created by Shinelon on 2017/12/11.
 */

class MainPresenter : BasePresenter {
    private var mainView: MainView? = null

    override fun setView(view: MainView?) {
        mainView = view
    }

    fun init(){
        mainView?.init()
    }

    fun itemSelted(item: MenuItem?) {
        mainView?.itemSelted(item)
    }

    fun itemNavSelected(item: MenuItem){
       mainView?.itemNavSelected(item)
    }

}
