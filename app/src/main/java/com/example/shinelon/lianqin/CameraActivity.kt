package com.example.shinelon.lianqin

import android.content.Intent
import android.hardware.Camera
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.Toast
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
    private var group_id = ""
    private var soundPool: SoundPool? = null
    private var id_1: Int = 0
    private var id_2 = 0
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_layout)
        setSupportActionBar(camera_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        prensenter = PhotoPresenter()
        group_id = intent.getStringExtra("group_id")
        initCamera(cameraId,group_id)
        soundPool = SoundPool(10,AudioManager.STREAM_MUSIC,0)
        id_1 = soundPool!!.load(this,R.raw.success,1)
        id_2 = soundPool!!.load(this,R.raw.failure,1)
        turn_camera.setOnClickListener{
            stopCamera()
            if (cameraId == 0) cameraId = 1 else cameraId = 0
            initCamera(cameraId,group_id)
        }
        prensenter!!.setView(this)
    }

    override fun init() {
    }

    fun initCamera(id: Int,group_id: String){
        camera = getCameraInstance(id)
        preview = CameraView(this,camera,group_id)
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
        if (camera == null) initCamera(cameraId,group_id)
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

    /**
     * 检测到人脸后选取图片识别时的状态框
     */
    override fun addFaceView(indicate: IndicateView){
        if(indicateView!=null) camera_container.removeView(indicateView)
        camera_container.addView(indicate)
        indicateView = indicate
    }

    /**
     * 识别结果出来后要移除方框
     */
    override fun removeFaceView(){
        if(indicateView!=null) camera_container.removeView(indicateView)
        indicateView = null
        preview!!.mark = 10
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

    fun startRecognize(image: String,group_id: String){
        prensenter!!.startRecognize(image,group_id)
    }

    override fun showToast(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessSound() {
        soundPool?.play(id_1,0.5F,0.5F,1,0,1F)
    }

    override fun showFailureSound() {
        soundPool?.play(id_2,0.5F,0.5F,1,0,1F)
    }

    override fun resetMark() {
        handler.postDelayed({preview?.resetMark()},1200)
    }
}