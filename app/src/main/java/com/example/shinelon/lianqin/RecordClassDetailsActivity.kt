package com.example.shinelon.lianqin

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.example.shinelon.lianqin.fragment.CLassDetailsFragment
import com.example.shinelon.lianqin.model.StudentSim
import kotlinx.android.synthetic.main.activity_record_calss_details.*
import kotlinx.android.synthetic.main.activity_record_details.*

/**
 * Created by Shinelon on 2018/2/8.
 */
class RecordClassDetailsActivity: AppCompatActivity() {
    val listTitle = listOf("缺勤学生","迟到学生","请假学生")

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

        text_class_details_number.text = total
        textchuqin.text = chuqin
        textqueqin.text = queqin
        textqinjia.text = qingjia
        textchidao.text = chidao

        val fm = supportFragmentManager

        val fg1 = CLassDetailsFragment.newInstance(arrayListOf(StudentSim("周嘉炜","2015000000")))
        val fg2 = CLassDetailsFragment.newInstance(arrayListOf(StudentSim("小红","2015111111")))
        val fg3 = CLassDetailsFragment.newInstance(arrayListOf(StudentSim("小明","2015222222")))
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
}