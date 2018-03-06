package com.example.shinelon.lianqin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import com.example.shinelon.lianqin.model.CourseTotalBean
import com.example.shinelon.lianqin.model.StudentDet
import com.example.shinelon.lianqin.model.StudentDetailsBean
import com.example.shinelon.lianqin.presenter.StudentDetailsPresenter
import com.example.shinelon.lianqin.view.StudentDetailsView
import kotlinx.android.synthetic.main.activity_student_details.*
import kotlinx.android.synthetic.main.list_student_details.view.*
import kotlin.properties.Delegates

/**
 * Created by Shinelon on 2018/2/8.
 */
class StudentDetailsActivity: AppCompatActivity(),StudentDetailsView {
    var img: ImageView? = null
    var textId: TextView? = null
    var textMajor: TextView? = null
    var que: TextView? = null
    var chi: TextView? = null
    var chu: TextView? = null
    var qin: TextView? = null
    var list = arrayListOf<StudentDetailsBean>()
    var presenter: StudentDetailsPresenter by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)
        setSupportActionBar(activity_student_details_toolbar)

        img = student_image
        textId = student_id
        textMajor = student_major
        que = student_queqin
        chi = student_chidao
        chu = student_chuqin
        qin = student_qinjia

        presenter = StudentDetailsPresenter()
        presenter.setView(this)

        val id = intent.getStringExtra("studentId")
        val teacherCourseId = intent.getIntExtra("teacherCourseId",0)
        textId?.text = id
        presenter.initList(list,id,teacherCourseId)

    }

    override fun init(major: String,q: String,c: String,qg: String,ch: String) {
        textMajor?.text = major
        que?.text = q
        chi?.text = c
        qin?.text = qg
        chu?.text = ch
    }

    override fun init() {
        val adapter = Adapter(list)
        recycler_student.layoutManager = LinearLayoutManager(this)
        recycler_student.adapter = adapter
    }

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val date  = v.student_date
        val time = v.student_time
        val week = v.student_week
        val record = v.student_record
        val order = v.class_order
    }

    inner class Adapter(l: ArrayList<StudentDetailsBean>): RecyclerView.Adapter<ViewHolder>(){
        val list = l
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val v = layoutInflater.inflate(R.layout.list_student_details,parent,false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            val str: List<String> = (list[position].attendanceTime).split(" ")
            holder?.date?.text = str[0]
            holder?.time?.text = str[1]
            holder?.order?.text = "第${list[position].classOrder}次课"
            holder?.week?.text ="第${list[position].weekNum.toString()}周"
            when(list[position].attendanceStatus){
                1->  holder?.record?.text = "出勤"
                2->  holder?.record?.text = "迟到"
                3->  holder?.record?.text = "请假"
                4->  holder?.record?.text = "旷课"
            }

        }

        override fun getItemCount() = list.size
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearView()
    }
}