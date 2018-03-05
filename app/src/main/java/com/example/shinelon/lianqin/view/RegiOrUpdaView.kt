package com.example.shinelon.lianqin.view

import android.app.Dialog
import com.example.shinelon.lianqin.view.BaseView

/**
 * Created by Shinelon on 2018/2/17.
 */
interface RegiOrUpdaView:BaseView{
    override fun init() {

    }
    fun showSuccessDialog()
    fun showFailureDialog(msg: String)
    fun showProgress()
    fun dismissProgress()
}