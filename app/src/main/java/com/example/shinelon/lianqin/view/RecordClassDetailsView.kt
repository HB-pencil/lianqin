package com.example.shinelon.lianqin.view

import com.example.shinelon.lianqin.model.StudentBean

/**
 * Created by Shinelon on 2018/3/4.
 */
interface RecordClassDetailsView: BaseView {
    fun initList(list1: ArrayList<StudentBean>?,list2: ArrayList<StudentBean>?,list3: ArrayList<StudentBean>?)
}