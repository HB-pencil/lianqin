package com.example.shinelon.lianqin.presenter

import android.os.Handler
import android.util.Log
import com.example.shinelon.lianqin.helper.RetrofitHelper
import com.example.shinelon.lianqin.model.AllNoteInfos
import com.example.shinelon.lianqin.model.IdentifyFace
import com.example.shinelon.lianqin.view.BaseView
import com.example.shinelon.lianqin.view.PhotoView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.*

/**
 * Created by Shinelon on 2018/2/15.
 */
class PhotoPresenter: BasePresenter {

    var photoView: PhotoView? = null
    var service: RetrofitHelper? = null
    var dispose: Disposable? = null
    var isBusy = false
    var courseClassId: Int = 0
    var studentNumber = ""
    var handler = Handler()

    override fun setView(baseView: BaseView?) {
        photoView = baseView as PhotoView
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://aip.baidubce.com/")
                .build()
        service = retrofit.create(RetrofitHelper::class.java)
    }
    override fun clearView() {
        photoView = null
        dispose?.dispose()
    }

    fun startRecognize(image: String,group_id: String){
        val _token = RequestBody.create(null,AllNoteInfos.token)
        val _group_id = RequestBody.create(null,group_id)
        val _image = RequestBody.create(null,image)

        service!!.recognizeFace(_token,_group_id,_image)
                .observeOn(AndroidSchedulers.mainThread())//观察者线程
                .subscribeOn(Schedulers.io())//耗时操作线程
                .subscribe(object: Observer<IdentifyFace>{
                    override fun onSubscribe(d: Disposable) {
                        Log.e("Rxjava","onSubscribe")
                        dispose = d
                    }

                    override fun onNext(t: IdentifyFace){
                        Log.e("Rxjava","onNext  $t")
                        if (t.result_num >= 1 ){
                            t.result.forEach {
                                if(it.scores[0]>=80){
                                    studentNumber = it.uid.split("_")[0]
                                    Log.e("Rxjava","onNext匹配分数为"+it.scores[0])
                                    photoView?.showSuccessSound()
                                    photoView?.removeFaceView() //移除方框
                                    photoView?.resetMark()//1.5秒后重置人脸识别
                                    Log.e("Rxjava",isBusy.toString())
                                    if(isBusy){
                                        completeRecord()
                                    }else{
                                        photoView?.showToast("考勤失败，无课！")
                                    }
                                    return //退出判断到最外层函数
                                }
                            }

                            Log.e("Rxjava","onNext匹配阈值小于80")
                            photoView?.showToast("识别出错，请重试！")
                            photoView?.showFailureSound()
                            photoView?.removeFaceView()
                            photoView?.resetMark()
                        }else{
                            Log.e("Rxjava","onNext没有匹配")
                            photoView?.showToast("识别出错，请重试！")
                            photoView?.showFailureSound()
                            photoView?.removeFaceView()
                            photoView?.resetMark()
                        }
                    }

                    override fun onComplete() {
                        Log.e("Rxjava","onComplete")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("Rxjava","onError"+e)
                    }
                })
    }

    fun completeRecord(){
        Log.e("考勤","开始执行")
        val pool: ExecutorService = ThreadPoolExecutor(1,1,0, TimeUnit.MILLISECONDS, LinkedBlockingQueue<Runnable>())
        val r =pool.submit{
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://119.29.193.41:81")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            val service = retrofit.create(RetrofitHelper::class.java)
            val json = "{\"studentNumber\":\"$studentNumber\",\"courseClassId\":$courseClassId}"
            val body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),json)
            val call = service.startRecord(body,AllNoteInfos.schoolToken)
            val result = call.execute()
            val rs = result.body()
            Log.e("考勤",rs.toString())
            handler.post {
                if(rs!!.message.isNotEmpty()) photoView?.showToast(rs.message)
            }
            if(rs!!.code==200){
                Log.e("考勤","已经记录"+rs.message)
            }
        }
    }

}