package com.example.shinelon.lianqin

import android.content.Intent
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.Toast
import com.example.shinelon.lianqin.model.ClassInfos
import kotlinx.android.synthetic.main.activity_choose_class.*
import kotlinx.android.synthetic.main.listview_choose_classs.view.*


/**
 *  Created by HB on 2018/1/5.
 */

class ChooseCalssActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_class)
        setSupportActionBar(choose_activity_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val c1 = ClassInfos("学期：17-1","班级：16计算机")
        val c2 = ClassInfos("17-1","16医工")
        val mList = mutableListOf<ClassInfos>(c1,c2)
        choose_recycler_view.layoutManager = LinearLayoutManager(this)
        choose_recycler_view.adapter = ChooseAdapter(mList)

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val layoutParams = choose_activity_toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams
        layoutParams.topMargin = getStatusBarHeight()
        layoutParams.bottomMargin = 0
        choose_activity_toolbar.layoutParams = layoutParams
        collapsing.minimumHeight = getActionBarHeight()
    }

    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(): Int{
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val rect = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rect)
        val h = dm.heightPixels - rect.height()
        Log.e("状态栏",h.toString())
        return h
    }


    /**
     * 获取标题栏高度
     */
    fun getActionBarHeight(): Int{
        //获取View绘制区域，好像不能用上面方法rect.top获取
        val h = supportActionBar!!.height
        Log.e("标题栏",h.toString())
        return h
    }

    /**
     * inner表示内部类，否则是嵌套类，无法访问外部变量
     */
    private inner class ChooseViewHolder(v: View): RecyclerView.ViewHolder(v){
        val semesterText = v.semester
        val classText = v.class_which
        init {
            v.setOnClickListener {
                Toast.makeText(v.context,"点击了班级：${v.class_which.text}",Toast.LENGTH_SHORT).show()
                jumpToSelect()
            }
        }
    }

    /**
     * 跳转到识别界面
     */
    fun jumpToSelect(){
        val intent = Intent(this,AddFaceActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
    }

    private inner class ChooseAdapter(l:MutableList<ClassInfos>): RecyclerView.Adapter<ChooseViewHolder>(){
        val list: MutableList<ClassInfos> = l

        override fun onBindViewHolder(holder: ChooseViewHolder?, position: Int) {
            holder?.semesterText?.text = list[position].semester
            holder?.classText?.text = list[position].classes
        }
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChooseViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.listview_choose_classs,parent,false)
            return ChooseViewHolder(view)
        }

        override fun getItemCount() = list.size
    }

}