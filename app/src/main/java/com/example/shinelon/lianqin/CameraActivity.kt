package com.example.shinelon.lianqin

import android.content.Intent
import android.hardware.Camera
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.RelativeLayout
import com.example.shinelon.lianqin.customview.CameraView
import com.example.shinelon.lianqin.customview.IndicateView
import com.example.shinelon.lianqin.presenter.PhotoPresenter
import com.example.shinelon.lianqin.view.PhotoView
import kotlinx.android.synthetic.main.camera_layout.*
import org.jetbrains.anko.centerInParent

/**
 * Created by HB on 2017/12/11.自定义相机Activity
 */
@SuppressWarnings("deprecation")
class CameraActivity: AppCompatActivity(),PhotoView{
    private var camera: Camera? = null
    private var preview: CameraView? = null
    private var indicateView: IndicateView? = null
    private var cameraId = 0
    private var prensenter: PhotoPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_layout)
        setSupportActionBar(camera_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initCamera(cameraId)

        turn_camera.setOnClickListener{
            stopCamera()
            if (cameraId == 0) cameraId = 1 else cameraId = 0
            initCamera(cameraId)
        }
        prensenter?.setView(this)
    }

    override fun init() {
    }

    fun initCamera(id: Int){
        camera = getCameraInstance(id)
        preview = CameraView(this,camera)
        val dm = resources.displayMetrics
        val w = dm.widthPixels
        val h = dm.heightPixels
        val p = camera_container.layoutParams as RelativeLayout.LayoutParams
        p.centerInParent()

        p.height = (h*0.74).toInt()
        val size = camera?.parameters?.supportedPreviewSizes?.last()
        val scale = size!!.height.toFloat()/size.width
        p.width = (scale * p.height).toInt()
        Log.e("TIPS","surfaceView的比例$scale")
        camera_container.layoutParams = p
        camera_container.addView(preview)
    }

    fun stopCamera(){
        camera?.stopFaceDetection()
        camera?.setFaceDetectionListener(null)
        camera?.setPreviewCallback(null)
        camera?.stopPreview()
        camera_container.removeView(preview)
        preview = null
        camera?.release()
        camera = null
    }

    /**
     * 恢复相机资源
     */
    override fun onResume() {
        super.onResume()
        if (camera == null) initCamera(cameraId)
        Log.w("onResume","success")
    }

    /**
     * 停止相机，释放资源
     */
    override fun onPause() {
        super.onPause()
        Log.w("onPause()","success")
        stopCamera()
        prensenter?.clearView()
    }


    fun getCameraInstance(id: Int): Camera{
        if(camera==null){
            try{
                camera = Camera.open(id)
            }catch (e: Exception){
                e.stackTrace
            }
        }
        Log.w("Camera",camera.toString())
        return camera!!
    }

    fun addView(indicate: IndicateView){
        if(indicateView!=null) camera_container.removeView(indicateView)
        camera_container.addView(indicate)
        indicateView = indicate
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_camera_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_check -> manualCheck()
        }
        return super.onOptionsItemSelected(item)
    }

    fun manualCheck(){
        val intent = Intent(this,ManualActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
    }
}