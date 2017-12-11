package com.example.shinelon.lianqin.listener

import android.graphics.RectF
import android.hardware.Camera
import android.util.Log
import com.example.shinelon.lianqin.CameraActivity
import com.example.shinelon.lianqin.customview.CameraView

/**
 * Created by Shinelon on 2017/12/11.
 */
class FaceDeListener(val cameraView: CameraView): Camera.FaceDetectionListener {
    override fun onFaceDetection(faces: Array<out Camera.Face>?, camera: Camera?) {
        if((faces!!.size)>0){
            Log.w("检测到人脸","success"+faces[0].rect.centerX()+"   "+faces[0].rect.centerY())
            faces.forEach {
                 //cameraView.drawFacesOnNext(it.rect)
            }
        }
    }
}