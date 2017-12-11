package com.example.shinelon.lianqin.view

import android.view.MenuItem

/**
 * Created by Shinelon on 2017/12/10.
 */

interface MainView : BaseView{
    fun itemSelted(item: MenuItem?)
    fun itemNavSelected(item: MenuItem?)
}
