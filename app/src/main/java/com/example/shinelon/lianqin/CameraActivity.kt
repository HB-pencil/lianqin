package com.example.shinelon.lianqin

import android.hardware.Camera
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.RelativeLayout
import com.example.shinelon.lianqin.customview.CameraView
import com.example.shinelon.lianqin.customview.IndicateView
import kotlinx.android.synthetic.main.camera_layout.*
import org.jetbrains.anko.centerInParent

/**
 * Created by HB on 2017/12/11.自定义相机Activity
 */
@SuppressWarnings("deprecation")
class CameraActivity: AppCompatActivity(){
    private var camera: Camera? = null
    private var preview: CameraView? = null
    private var indicateView: IndicateView? = null
    private var cameraId = 0

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
    }

    fun initCamera(id: Int){
        camera = getCameraInstance(id)
        preview = CameraView(this,camera)
        val dm = resources.displayMetrics
        val w = dm.widthPixels
        val h = dm.heightPixels
        val p = camera_container.layoutParams as RelativeLayout.LayoutParams
        p.centerInParent()
        p.width = (w*0.8).toInt()
        p.height = (h*0.6).toInt()
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


    override fun onResume() {
        super.onResume()
        if (camera == null) initCamera(cameraId)
        Log.w("onResume","success")
    }

    override fun onPause() {
        super.onPause()
        Log.w("onPause()","success")
        stopCamera()
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
}