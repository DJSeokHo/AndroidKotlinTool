package com.swein.androidkotlintool.main.examples.systemphotopicker

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.glide.SHGlide
import com.swein.androidkotlintool.framework.util.log.ILog


class SystemPhotoPickerExampleActivity : AppCompatActivity() {

    private val systemPhotoPickManager = SystemPhotoPickManager(this)

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_system_photo_picker_example)

        findViewById<Button>(R.id.button).setOnClickListener {

            systemPhotoPickManager.requestPermission {

//                it.selectPicture { uri ->
//                    SHGlide.setImage(imageView, uri = uri)
//                }

                it.selectPathPicture { filePath ->
                    ILog.debug("???", filePath)
                    SHGlide.setImage(imageView, filePath = filePath)
                }

//                it.takePictureWithFilePath(true) { filePath ->
//                    SHGlide.setImage(imageView, filePath = filePath)
//                }

//            it.takePictureWithUri { uri ->
//                SHGlide.setImage(imageView, uri = uri)
//            }

//            it.takePictureWithBitmap { bitmap ->
//                SHGlide.setImage(imageView, bitmap = bitmap)
//            }

            }

        }
    }

}