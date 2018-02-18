package com.example.shinelon.lianqin

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.example.shinelon.lianqin.fragment.MyDialog
import com.example.shinelon.lianqin.presenter.RegiOrUpdatePresener
import com.example.shinelon.lianqin.view.RegiOrUpdaView
import kotlinx.android.synthetic.main.activity_register_fupdate_ace.*
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * Created by Shinelon on 2018/2/17.
 */
class RegisterOrUpdateFaceActivity: AppCompatActivity(),RegiOrUpdaView{

    val pfile = File(Environment.getExternalStorageDirectory(),
    "LianQin")
    var path:String = ""
    var image: String = ""
    var prensener: RegiOrUpdatePresener? = null
    var group_id: String = ""
    var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_fupdate_ace)
        setSupportActionBar(activity_register_face_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        prensener = RegiOrUpdatePresener()
        prensener?.setView(this)
        group_id = intent.getStringExtra("group_id")
        type = intent.getStringExtra("action_type")

        if (!pfile.exists()) pfile.mkdirs()
        take_photo.setOnClickListener {
            val file = File(pfile,
                    "lianqin"+System.currentTimeMillis()+".jpg")
            if(file.exists()){
                file.createNewFile()
            }
            path = file.absolutePath
            val uri: Uri
            if(Build.VERSION.SDK_INT >= 24){
                uri = FileProvider.getUriForFile(this,packageName+".myProvider",file)
            }else{
                uri = Uri.fromFile(file)
            }
            val i = Intent()
            i.action = MediaStore.ACTION_IMAGE_CAPTURE
            i.putExtra(MediaStore.EXTRA_OUTPUT,uri)
            Log.e("拍照uri",uri.toString())
            //i.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            //i.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            startActivityForResult(i,0)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            sendUpdateInfo(uri)
        }

        select_photo.setOnClickListener {
            val i = Intent()
            i.action = Intent.ACTION_PICK
            i.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivityForResult(i,1)
        }

        finish_register.setOnClickListener {
            val num = student_id_face.text.toString()+"_lq"
            val name = student_name_face.text.toString()
            val major = student_major_face.text.toString()
            val str = "姓名为"+name+"班级为"+major
            if(num!=""&&name!=""&&major!=""){
                Log.w("注册/更新前group信息",group_id)
                prensener?.registerFace(group_id,num,str,image,type)
            }else{
                Toast.makeText(this,"资料不能留空！",Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            0 -> if(resultCode == Activity.RESULT_OK){
               // val file = File(path)
                val bitmap = BitmapFactory.decodeFile(path)
                student_image_register.setImageBitmap(bitmap)
                val out = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG,50,out)
                val array = out.toByteArray()
                image = Base64.encodeToString(array,Base64.NO_WRAP)

                Log.e("拍照图片",image)
            }
            1 -> if(resultCode == Activity.RESULT_OK){
                val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(data?.data))
                student_image_register.setImageBitmap(bitmap)
                val out = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG,50,out)
                val array = out.toByteArray()
                image = Base64.encodeToString(array,Base64.NO_WRAP)

                Log.e("选取图片大小",image)
            }
        }
    }

    fun sendUpdateInfo(uri: Uri) {
        val intentNotify = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        intentNotify.data = uri
        sendBroadcast(intentNotify)
    }

    /**
     * 可以将file转为content://
     */
    fun getImageContentUri(context: Context, imageFile: File): Uri? {
        val filePath = imageFile.absolutePath
        val cursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Images.Media._ID),
                MediaStore.Images.Media.DATA + "=? ",
                arrayOf(filePath), null)

        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID))
            val baseUri = Uri.parse("content://media/external/images/media")
            return Uri.withAppendedPath(baseUri, "" + id)
        } else {
            if (imageFile.exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, filePath)
                return context.contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            } else {
                return null
            }
        }



    }

    override fun onStop() {
        super.onStop()
        prensener?.clearView()
    }

    override fun showSuccessDialog(){
        var str = ""
        if (type=="append") str = "注册/追加"
        else str = "重置"
        val dialog = MyDialog.newInstance(str+"成功！")
        dialog.show(supportFragmentManager,"")
    }

    override fun showFailureDialog(msg: String){
        var str = ""
        if (type=="append") str = "注册/追加"
        else str = "重置"
        val dialog = MyDialog.newInstance(str+"失败，请重试！错误为："+msg)
        dialog.show(supportFragmentManager,"")
    }

}