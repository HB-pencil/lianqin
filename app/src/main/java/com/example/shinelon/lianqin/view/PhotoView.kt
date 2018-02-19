package com.example.shinelon.lianqin.view

import com.example.shinelon.lianqin.customview.IndicateView

/**
 * Created by Shinelon on 2018/2/15.
 */
interface PhotoView:BaseView{
    fun removeFaceView()
    fun addFaceView(indicateView: IndicateView)
    fun showToast(msg: String)
    fun showSuccessSound()
    fun showFailureSound()
    fun resetMark()
}