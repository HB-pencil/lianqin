package com.example.shinelon.lianqin

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Rect
import android.graphics.RectF
import android.opengl.Visibility
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.example.shinelon.lianqin.model.AllNoteInfos
import com.example.shinelon.lianqin.model.ClassInfos
import kotlinx.android.synthetic.main.activity_choose_class.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.listview_choose_classs.view.*


/**
 *  Created by HB on 2018/1/5.
 */

class ChooseCalssActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_class)

        val mList = AllNoteInfos.classInfoList
        choose_recycler_view.layoutManager = LinearLayoutManager(this)
        choose_recycler_view.adapter = ChooseAdapter(mList)

        //这个鬼toolbar的使用真是迷的不行！而且要在set之前设置title
        choose_activity_toolbar.title = ""
        setSupportActionBar(choose_activity_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        choose_activity_toolbar.setNavigationOnClickListener {
            finish()
        }

        appBarLayout.addOnOffsetChangedListener{
            appBarLayout, verticalOffset ->    if (verticalOffset == 0) {
            //不要试图在CollapsingToolbarLayout使用toolbar的title = =又是个官方bug
            collapsing.title = ""
            Log.e("AppBar == 0", "监听完全展开$verticalOffset")
        }
        else if (Math.abs(verticalOffset) >= appBarLayout!!.totalScrollRange) {
            collapsing.title = "选择班级"
            Log.e("AppBar > 0", "监听收缩$verticalOffset")
        }
        }

    }

    /**
     * 注意，必须等到此方法，View才可以被正确测量，因为此时view才完成他的布局
     */

       override fun onWindowFocusChanged(hasFocus: Boolean) {
           super.onWindowFocusChanged(hasFocus)
           val layoutParams = choose_activity_toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams
           layoutParams.topMargin = getStatusBarHeight()
           layoutParams.bottomMargin = 0
           choose_activity_toolbar.layoutParams = layoutParams
           collapsing.minimumHeight = getActionBarHeight()
       }

    /**
     * 获取状态栏高度，注意，不要试图在CoordinatorLayout中不要使用fitSystemWindows这个属性
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
        val classDetails = v.class_details
        var teacherCourseId: Int = 0
        init {
            v.setOnClickListener {
                jumpToSelect(teacherCourseId)
            }
        }
    }

    /**
     * 跳转到识别界面
     */
    fun jumpToSelect(teacherCourseId: Int){
        val intent = Intent(this,AddFaceActivity::class.java)
        Log.w("课程id",teacherCourseId.toString())
        intent.putExtra("banji","kechengid_"+teacherCourseId)
        intent.putExtra("teacherCourseId",teacherCourseId)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
    }

    private inner class ChooseAdapter(l:MutableList<ClassInfos>): RecyclerView.Adapter<ChooseViewHolder>(){
        val list: MutableList<ClassInfos> = l

        override fun onBindViewHolder(holder: ChooseViewHolder?, position: Int) {
            holder?.semesterText?.text = list[position].semester
            holder?.classText?.text = list[position].classes
            holder?.classDetails?.text = list[position].details
            holder?.teacherCourseId = list[position].courseId
        }
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChooseViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.listview_choose_classs,parent,false)
            return ChooseViewHolder(view)
        }

        override fun getItemCount() = list.size
    }

}