package com.example.shinelon.lianqin

import android.hardware.Camera
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Surface
import android.widget.RelativeLayout
import com.example.shinelon.lianqin.customview.CameraView
import kotlinx.android.synthetic.main.camera_layout.*
import org.jetbrains.anko.centerInParent

/**
 * Created by Shinelon on 2017/12/11.
 */
class CameraActivity: AppCompatActivity() {
    private var camera: Camera? = null
    private var preview: CameraView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_layout)
        setSupportActionBar(camera_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        camera = getCameraInstance()
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

    override fun onResume() {
        super.onResume()
        camera = getCameraInstance()
    }

    override fun onPause() {
        super.onPause()
        camera?.release()
    }

    fun getCameraInstance(): Camera{
        if(camera==null){
            try{
                camera = Camera.open(0)
            }catch (e: Exception){
                e.stackTrace
            }
        }
        Log.w("Camera",camera.toString())
        return camera!!
    }


}