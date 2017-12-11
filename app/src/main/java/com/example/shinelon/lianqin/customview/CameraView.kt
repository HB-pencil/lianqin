package com.example.shinelon.lianqin.customview

import android.content.Context
import android.graphics.*
import android.hardware.Camera
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.shinelon.lianqin.listener.FaceDeListener

/**
 * Created by Shinelon on 2017/12/11.
 */
class CameraView(context: Context?,c:Camera?): SurfaceView(context),SurfaceHolder.Callback,Camera.PreviewCallback{
    private var camera: Camera? = null
    private var mHolder: SurfaceHolder? = null
    private val mPaint = Paint()
    init {
        camera = c
        mHolder = holder
        mHolder?.addCallback(this)
        mHolder?.setFormat(PixelFormat.TRANSPARENT)
        mPaint.color = Color.RED
        mPaint.strokeWidth = 3F
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
    }


    override fun surfaceCreated(holder: SurfaceHolder?) {
        Log.w("created","success")
        //一系列初始化
        val p = camera?.parameters
        val fousMode: List<String> = p!!.supportedFocusModes

        if(fousMode.contains(Camera.Parameters.FOCUS_MODE_AUTO))
            p.focusMode = Camera.Parameters.FOCUS_MODE_AUTO

        //调试
        val pc = p.supportedPictureSizes
        val pv = p.supportedPreviewSizes

        p.setPreviewSize(pv[pv.size/2].width,pv[pv.size/2].height)

        Log.e("PictureSize和PreviewSize","${pc.size}和${pv.size}")
        pc.forEach {
            Log.e("Picture大小","宽${it.width}高${it.height}")
        }
        pv.forEach {
            Log.e("Preview大小","宽${it.width}高${it.height}")
        }

        camera?.parameters = p
        camera?.setDisplayOrientation(90)
        //使用观察者模式
        val listener = FaceDeListener(this)
        camera?.setFaceDetectionListener(listener)
        //camera.takePicture()拍照的动作
        camera?.setPreviewCallback(this)

        // The Surface has been created, now tell the camera where to draw the preview.
        try{
            camera?.setPreviewDisplay(mHolder)
            camera?.startPreview()
            //startPreview之后才可以进行
            startFaceDetection()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
/**
    fun drawFacesOnNext(rect: Rect){
        Thread{
            val canvas = mHolder?.lockCanvas()
            canvas?.drawRect(rect,mPaint)
            mHolder?.unlockCanvasAndPost(canvas)
        }.start()
    }
*/
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        Log.w("changed","success")
        if(mHolder?.surface == null) return

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        Log.w("destroyed","success")
    }

    /**
     * 预览回调用，预览分辨率
     */
    override fun onPreviewFrame(data: ByteArray?, camera: Camera?) {
        Log.w("实时帧","success,size=${data?.size}")
    }

    fun startFaceDetection(){
        val p = camera?.parameters
        if((p!!.maxNumDetectedFaces)> 0) {
            camera?.startFaceDetection()
            Log.e("支持人脸检测", "success")
        }else{
            Log.e("不支持人脸检测", "fail")
        }
    }


}