package com.example.shinelon.lianqin.listener

import android.hardware.Camera
import android.util.Log
import com.example.shinelon.lianqin.CameraActivity
import com.example.shinelon.lianqin.customview.CameraView
import com.example.shinelon.lianqin.customview.IndicateView

/**
 * Created by HB on 2017/12/20.
 */
class FaceDeListener(val activity: CameraActivity,val cameraView: CameraView): Camera.FaceDetectionListener {

    override fun onFaceDetection(faces: Array<Camera.Face>?, camera: Camera?) {
        Log.w("检测到人脸","${faces?.size}")
        faces?.forEach {
            Log.e("rect","左${it.rect.left}顶${it.rect.top}右${it.rect.right}下${it.rect.bottom}")
            if(cameraView.mark <= 3){
                cameraView.isFace = true
                activity.addFaceView(IndicateView(activity,it.rect))
            }
        }
    }
}