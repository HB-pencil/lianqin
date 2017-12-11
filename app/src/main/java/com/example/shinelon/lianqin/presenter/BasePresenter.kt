package com.example.shinelon.lianqin.presenter

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

import com.example.shinelon.lianqin.view.MainView

/**
 * Created by Shinelon on 2017/12/10.
 */

interface BasePresenter {
    fun setView(mainView: MainView?)
}
