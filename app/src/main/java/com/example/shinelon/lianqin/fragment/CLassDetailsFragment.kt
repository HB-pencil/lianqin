package com.example.shinelon.lianqin.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shinelon.lianqin.R
import com.example.shinelon.lianqin.StudentDetailsActivity
import com.example.shinelon.lianqin.model.StudentBean
import kotlinx.android.synthetic.main.list_class_details.view.*
import kotlinx.android.synthetic.main.recycler_class_details.view.*
import java.io.NotSerializableException
import kotlin.properties.Delegates

/**
 * Created by Shinelon on 2018/2/9.
 */
class CLassDetailsFragment: Fragment() {
    var list: ArrayList<StudentBean>? = null
    var teacherCourseId: Int? = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.recycler_class_details,container,false)

        try {
            val list = this.arguments?.getSerializable("data") as ArrayList<StudentBean>
            teacherCourseId = this.arguments?.getInt("teacherCourseId",0)

            val recyclerView = v.recycler_class_details
            recyclerView.layoutManager = LinearLayoutManager(activity)
            val adp = Adapter(list)
            recyclerView.adapter = adp
        }catch (e: NotSerializableException){
            e.printStackTrace()
        }
        return v
    }

    companion object {
        fun newInstance(list: ArrayList<StudentBean>?,teaCourseId: Int): CLassDetailsFragment{
            val fragment = CLassDetailsFragment()
            val bundle = Bundle()
            if(list!=null){
                bundle.putSerializable("data",list)
            }
            bundle.putInt("teacherCourseId",teaCourseId)
            fragment.arguments = bundle
            return fragment
        }
    }

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val name = v.class_details_name
        val button = v.class_details_button
        var id by Delegates.notNull<String>()

        init {
            button.setOnClickListener{
                val i = Intent(activity,StudentDetailsActivity::class.java)
                i.putExtra("studentId",id)
                i.putExtra("teacherCourseId",teacherCourseId)
                startActivity(i)
                activity!!.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            }
        }
    }

    inner class Adapter(val list: ArrayList<StudentBean>?): RecyclerView.Adapter<ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val v =layoutInflater.inflate(R.layout.list_class_details,parent,false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
           if(list!=null){
               holder?.name?.text = list[position].name
               holder?.id = list[position].studentNumber
           }
        }

        override fun getItemCount(): Int{
            when(list==null){
                true -> return 0
                false -> return list!!.size
            }
        }
    }

}