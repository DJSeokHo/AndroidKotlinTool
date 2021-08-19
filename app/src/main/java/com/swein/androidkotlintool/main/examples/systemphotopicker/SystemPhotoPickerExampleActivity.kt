package com.swein.androidkotlintool.main.examples.systemphotopicker

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.glide.SHGlide


class SystemPhotoPickerExampleActivity : AppCompatActivity() {

    private val systemPhotoPickManager = SystemPhotoPickManager(this)

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_system_photo_picker_example)

        findViewById<Button>(R.id.button).setOnClickListener {

//            systemPhotoPickManager.selectPicture {
//                SHGlide.setImage(imageView, uri = it)
//            }

            systemPhotoPickManager.takePictureWithFilePath(true) {
                SHGlide.setImage(imageView, filePath = it)
            }

//            systemPhotoPickManager.takePictureWithUri {
//                SHGlide.setImage(imageView, uri = it)
//            }

//            systemPhotoPickManager.takePictureWithBitmap {
//                SHGlide.setImage(imageView, bitmap = it)
//            }

        }
    }

}