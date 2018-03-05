package com.example.shinelon.lianqin

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.shinelon.lianqin.model.ClassDetails
import com.example.shinelon.lianqin.model.ClassInfos
import com.example.shinelon.lianqin.model.TotalSim
import com.example.shinelon.lianqin.presenter.RecordPresenter
import com.example.shinelon.lianqin.view.RecordView
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.android.synthetic.main.activity_record_details.*
import kotlinx.android.synthetic.main.list_record_details.view.*
import kotlinx.android.synthetic.main.listview_choose_classs.view.*
import kotlin.properties.Delegates

/**
 * Created by Shinelon on 2018/2/8.
 */
class RecordDetailsActivity: AppCompatActivity(),RecordView {
    var presenter: RecordPresenter by Delegates.notNull()
    val mList = mutableListOf<ClassDetails>()
    var tId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_details)
        setSupportActionBar(activity_record_details_toolbar)
        val s = intent.getStringExtra("semester")
        val id = intent.getIntExtra("teacherCourseId",0)
        supportActionBar?.title = s
        presenter = RecordPresenter()
        presenter.setView(this)
        presenter.initList(mList,id)
    }

    override fun init() {
        recycler_record_details.layoutManager = LinearLayoutManager(this)
        recycler_record_details.adapter = RecordAdapter(mList)
    }

    override fun setTeacherCourseId(id: Int) {
        tId = id
        Log.e("teacherCourseId",tId.toString())
    }

    override fun updateHeader(p1: Int, p2: Int, p3: Int, p4: Int, p5: Int) {
        sum_record.text = p1.toString()
        consume_number.text = p2.toString()
        queqin.text = p3.toString()
        chidao.text = p4.toString()
        qinjia.text = p5.toString()

    }

    private inner class RecordViewHolder(v: View): RecyclerView.ViewHolder(v){
        val semester = v.record_time
        val week = v.record_week
        val number = v.number_class
        val chuqin = v.chuqin
        val queqin = v.queqin
        val chidao = v.chidao
        val qinjia = v.qinjia
        val button = v.bt_record_details
        var order = 0

        init {
            button.setOnClickListener {
                jumpToRecordDetailsMore("第${order}次课",number.text.toString(),chuqin.text.toString(),
                        queqin.text.toString(), chidao.text.toString(),qinjia.text.toString())
            }
        }
    }

    /**
     * 跳转到详细界面
     */
    fun jumpToRecordDetailsMore(details: String,total: String,quc: String,que: String,chi: String,qing: String){
        val intent = Intent(this,RecordClassDetailsActivity::class.java)
        intent.putExtra("details",details)
        intent.putExtra("total",total)
        intent.putExtra("quc",quc)
        intent.putExtra("que",que)
        intent.putExtra("chi",chi)
        intent.putExtra("qing",qing)
        intent.putExtra("teacherCourseId",tId)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
    }

    private inner class RecordAdapter(l:MutableList<ClassDetails>): RecyclerView.Adapter<RecordViewHolder>(){
        val list: MutableList<ClassDetails> = l

        override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
            holder.semester.text = list[position].time
            holder.number.text = list[position].number.toString()
            holder.chidao.text = list[position].chidao.toString()
            holder.chuqin.text = list[position].chuqin.toString()
            holder.queqin.text = list[position].queqin.toString()
            holder.qinjia.text = list[position].qinjia.toString()
            holder.week.text = list[position].week
            holder.order = list[position].order
        }
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecordViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_record_details,parent,false)
            return RecordViewHolder(view)
        }

        override fun getItemCount() = list.size
    }

    override fun onStop() {
        super.onStop()
        presenter.clearView()
    }
}