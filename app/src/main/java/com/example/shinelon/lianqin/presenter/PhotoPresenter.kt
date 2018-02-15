package com.example.shinelon.lianqin.presenter

import com.example.shinelon.lianqin.view.BaseView
import com.example.shinelon.lianqin.view.PhotoView

/**
 * Created by Shinelon on 2018/2/15.
 */
class PhotoPresenter: BasePresenter {

    var photoView: PhotoView? = null
    override fun setView(baseView: BaseView?) {
        photoView = baseView as PhotoView
    }
    override fun clearView() {
       photoView = null
    }
}