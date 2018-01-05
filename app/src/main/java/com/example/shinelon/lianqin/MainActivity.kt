package com.example.shinelon.lianqin

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.shinelon.lianqin.actionProviders.messageActionProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

/**
 * created by HB,主界面Activity,作为View
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private var provider: messageActionProvider? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        choose_bj.setOnClickListener {
            Toast.makeText(this,"点击",Toast.LENGTH_SHORT).show()
            if(checkCameraHareWare()) {
                val intent = Intent(this,CameraActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    /**
     * 检测有无相机
     */
    fun checkCameraHareWare() = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)


    override fun onResume() {
        super.onResume()
        choose_bj.invalidate()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        val menuItem = menu?.findItem(R.id.action_message) as MenuItem
        provider = MenuItemCompat.getActionProvider(menuItem) as messageActionProvider
        Log.d("Listener","provider调用")
        provider?.setListener {
            provider?.hideCircle()
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_settings->{
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }
            R.id.action_exit->{

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item?.itemId) {
            R.id.nav_census -> {
                // Handle the camera action
            }
            R.id.nav_course->{

            }
            R.id.nav_info->{

            }
            R.id.nav_mode->{
                if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                recreate()
            }
            R.id.nav_manage->{
                val intent3 = Intent(this,SettingActivity::class.java)
                startActivity(intent3)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }

        }
        return true
    }
}
