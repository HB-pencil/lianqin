package com.example.shinelon.lianqin

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.content.PermissionChecker
import android.support.v4.view.GravityCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.shinelon.lianqin.actionProviders.messageActionProvider
import com.example.shinelon.lianqin.helper.PermissionsChecker
import com.example.shinelon.lianqin.listener.ActionProviderListener
import com.example.shinelon.lianqin.model.AllNoteInfos
import com.example.shinelon.lianqin.presenter.MainPresenter
import com.example.shinelon.lianqin.view.MainView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

/**
 * created by HB,主界面Activity,作为View
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,MainView{
    private var provider: messageActionProvider? = null
    private val mChecker = PermissionsChecker()
    private var dialog: AlertDialog? = null
    private var presenter: MainPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        presenter = MainPresenter()
        presenter?.setView(this)
        presenter?.init()

        fab.setOnClickListener { view ->
            val i = Intent(this,NoteActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }

        choose_bj.setOnClickListener {
            Toast.makeText(this,"点击",Toast.LENGTH_SHORT).show()
            if(checkCameraHareWare()) {
                val intent = Intent(this,ChooseCalssActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        val handler = Handler()
        handler.post{
            presenter?.getClassDetailsResult()
        }

    }

    override fun init() {

    }

    /**
     * 检测有无相机
     */
    fun checkCameraHareWare() = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)

    override fun onStart() {
        super.onStart()
        if(Build.VERSION.SDK_INT>=23){
            val permissions = arrayOf("android.permission.CAMERA","android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE")
            mChecker.checkPermission(this,permissions)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
        presenter?.clearView()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==1&&Build.VERSION.SDK_INT>=23){
            grantResults.forEach {
                if(it==PackageManager.PERMISSION_DENIED){
                    if(!shouldShowRequestPermissionRationale(permissions[grantResults.indexOf(it)])){
                        showWarnDialog()
                    }else{
                        finish()
                        Log.e("次数","+++++++++++");
                    }
                }
            }
        }

    }

    /**
     * 用户永久拒绝权限提示框
     */
    fun showWarnDialog(){
        if(dialog==null){
            dialog = AlertDialog.Builder(this)
                    .setTitle("警告！")
                    .setMessage("为了让软件正常工作，请您允许通过申请的权限，否则将无法提供服务！")
                    .setPositiveButton("设置"){
                        dialogInterface, i ->  startSetting()
                    }
                    .setNegativeButton("退出"){
                        dialogInterface, i -> finish()
                    }
                    .create()

            dialog?.setCancelable(false)
            dialog?.setCanceledOnTouchOutside(false)
            dialog?.show()
        }

    }

    fun startSetting(){
        val i = Intent()
        i.action=ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package",this.packageName,null)
        i.data = uri
        startActivity(i)
    }

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
        Log.d("Listener","provider调用"+provider.toString())

        //有毒啊这里！！！4.4 居然不能直接provider.setListener{}
        provider!!.setListener(object: ActionProviderListener{
            override fun onClick(p0: View?) {
                if(provider!!.hasMessage){
                    provider!!.hideCircle()
                }
                startMyBroadcast()
            }
        })
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
        when (item.itemId) {
            R.id.nav_census -> {
                val i = Intent(this,RecordActivity::class.java)
                startActivity(i)
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            }
            R.id.nav_course->{

            }
            R.id.nav_info->{
                val intent = Intent(this,PublicBroadcastViewActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
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

    fun startMyBroadcast(){
        val i = Intent(this, PrivateBroadcastViewActivity::class.java)
        startActivity(i)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        description.text = AllNoteInfos.teacherName
    }
}
