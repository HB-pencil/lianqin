package com.example.shinelon.lianqin

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.shinelon.lianqin.model.AllNoteInfos
import com.example.shinelon.lianqin.model.ClassInfos
import com.example.shinelon.lianqin.presenter.RecordPresenter
import com.example.shinelon.lianqin.view.RecordView
import kotlinx.android.synthetic.main.activity_choose_class.*
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.android.synthetic.main.list_record.view.*
import kotlinx.android.synthetic.main.listview_choose_classs.view.*
import kotlin.properties.Delegates

/**
 * Created by Shinelon on 2018/2/8.
 */
class RecordActivity: AppCompatActivity(){
    var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        setSupportActionBar(activity_record_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val mList = AllNoteInfos.classInfoList
        handler.postDelayed({
            recycler_record.layoutManager = LinearLayoutManager(this)
            recycler_record.adapter = RecordAdapter(mList)
        },500)
    }
    private inner class RecordViewHolder(v: View): RecyclerView.ViewHolder(v){
        val semesterText = v.text_list_record
        val classText = v.text_list_record_class
        val classDetails = v.text_list_record_details
        var teacherCourseId = 0
        init {
            v.setOnClickListener {
                jumpToRecordDetails(classText.text.toString(),teacherCourseId)
            }
        }
    }

    /**
     * 跳转到详细班级情况界面
     */
    fun jumpToRecordDetails(semester: String,teacherCourseId: Int){
        val intent = Intent(this,RecordDetailsActivity::class.java)
        intent.putExtra("semester",semester)
        intent.putExtra("teacherCourseId",teacherCourseId)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
    }

    private inner class RecordAdapter(l:MutableList<ClassInfos>): RecyclerView.Adapter<RecordViewHolder>(){
        val list: MutableList<ClassInfos> = l

        override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
            holder.semesterText?.text = list[position].semester
            holder.classText?.text = list[position].classes
            holder.classDetails?.text = list[position].details
            holder.teacherCourseId = list[position].courseId
        }
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecordViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_record,parent,false)
            return RecordViewHolder(view)
        }

        override fun getItemCount() = list.size
    }


}