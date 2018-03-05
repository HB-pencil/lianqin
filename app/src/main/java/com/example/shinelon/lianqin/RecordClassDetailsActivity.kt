package com.example.shinelon.lianqin

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import com.example.shinelon.lianqin.fragment.CLassDetailsFragment
import com.example.shinelon.lianqin.model.StudentBean
import com.example.shinelon.lianqin.presenter.RecordClassDetailsPresenter
import com.example.shinelon.lianqin.view.RecordClassDetailsView
import kotlinx.android.synthetic.main.activity_record_calss_details.*
import kotlin.properties.Delegates

/**
 * Created by Shinelon on 2018/2/8.
 */
class RecordClassDetailsActivity: AppCompatActivity(),RecordClassDetailsView {
    val listTitle = listOf("缺勤学生","迟到学生","请假学生")
    var listLate: ArrayList<StudentBean>? = null
    var listAbsence: ArrayList<StudentBean>? = null
    var listLeave: ArrayList<StudentBean>? = null
    var presenter by Delegates.notNull<RecordClassDetailsPresenter>()
    var tId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_calss_details)
        setSupportActionBar(activity_record_class_details_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val details = intent.getStringExtra("details")
        supportActionBar?.title = details

        val total = intent.getStringExtra("total")
        val chuqin = intent.getStringExtra("quc")
        val queqin = intent.getStringExtra("que")
        val chidao = intent.getStringExtra("chi")
        val qingjia = intent.getStringExtra("qing")

        presenter = RecordClassDetailsPresenter()
        presenter.setView(this)

        text_class_details_number.text = total
        textchuqin.text = chuqin
        textqueqin.text = queqin
        textqinjia.text = qingjia
        textchidao.text = chidao

        tId = intent.getIntExtra("teacherCourseId",0)
        presenter.initList(tId)
    }

    override fun initList(list1: ArrayList<StudentBean>?, list2: ArrayList<StudentBean>?, list3: ArrayList<StudentBean>?) {
        listLate = list1
        listAbsence = list2
        listLeave = list3
    }

    override fun init() {
        val fm = supportFragmentManager
        val fg1 = CLassDetailsFragment.newInstance(listAbsence,tId)
        val fg2 = CLassDetailsFragment.newInstance(listLate,tId)
        val fg3 = CLassDetailsFragment.newInstance(listLeave,tId)
        val list = mutableListOf<Fragment>(fg1,fg2,fg3)
        val pagerAdapter = ViewPagerAdapter(list,fm)
        viewpager.adapter = pagerAdapter
        tab_layout.tabMode = TabLayout.MODE_FIXED
        tab_layout.setupWithViewPager(viewpager)
    }

    /**
    inner class ViewPagerAdapter: PagerAdapter(){
        override fun isViewFromObject(view: View, `object`: Any): Boolean {

        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            return super.instantiateItem(container, position)
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            super.destroyItem(container, position, `object`)
        }

        override fun getCount(): Int {

        }

        override fun getPageTitle(position: Int): CharSequence? {
            return listTitle[position]
        }
    }
    **/

    inner class ViewPagerAdapter(val fragmentList: MutableList<Fragment>,val fm: FragmentManager): FragmentStatePagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listTitle[position]
    }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearView()
    }
}