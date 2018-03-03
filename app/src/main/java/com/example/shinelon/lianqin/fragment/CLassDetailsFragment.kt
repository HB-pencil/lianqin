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
import com.example.shinelon.lianqin.model.StudentSim
import kotlinx.android.synthetic.main.list_class_details.view.*
import kotlinx.android.synthetic.main.recycler_class_details.view.*
import kotlin.properties.Delegates

/**
 * Created by Shinelon on 2018/2/9.
 */
class CLassDetailsFragment: Fragment() {
    var list = arrayListOf<StudentSim>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.recycler_class_details,container,false)

        list = this.arguments?.getSerializable("data") as ArrayList<StudentSim>
        val recyclerView = v.recycler_class_details
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adp = Adapter(list)
        recyclerView.adapter = adp

        return v
    }

    companion object {
        fun newInstance(list: ArrayList<StudentSim>): CLassDetailsFragment{
            val fragment = CLassDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable("data",list)
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
                startActivity(i)
                activity!!.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            }
        }
    }

    inner class Adapter(val list: ArrayList<StudentSim>): RecyclerView.Adapter<ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val v =layoutInflater.inflate(R.layout.list_class_details,parent,false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.name?.text = list[position].name
            holder?.id = list[position].id
        }

        override fun getItemCount() = list.size
    }

}