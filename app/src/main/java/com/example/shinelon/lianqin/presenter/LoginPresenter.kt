package com.example.shinelon.lianqin.presenter

import com.example.shinelon.lianqin.view.BaseView
import com.example.shinelon.lianqin.view.LoginView
import com.example.shinelon.lianqin.view.MainView

/**
 * Created by HB on 2018/1/4.
 */
class LoginPresenter: BasePresenter {
    var view: LoginView? = null
    override fun setView(baseView: BaseView?) {
        view = baseView as LoginView
    }

    fun checkAccount(){
        view?.loginSystem()
    }

    override fun clearView() {
        view = null
    }
}