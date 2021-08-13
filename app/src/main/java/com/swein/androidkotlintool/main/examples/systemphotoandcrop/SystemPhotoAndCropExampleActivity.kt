package com.swein.androidkotlintool.main.examples.systemphotoandcrop

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.swein.androidkotlintool.R

class SystemPhotoAndCropExampleActivity : AppCompatActivity() {

//    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
//        override fun createIntent(context: Context, input: Any?): Intent {
//            return CropImage.activity().setAspectRatio(16, 9)
//                .getIntent(this@SystemPhotoAndCropExampleActivity)
//        }
//
//        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
//            return CropImage.getActivityResult(intent)?.uri
//        }
//
//    }
//
//    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>

//    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        // Handle the returned Uri
//        uri?.let {
//            imageView.setImageURI(it)
//        }
//    }

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            // Handle the Intent
        }
    }

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_photo_and_crop_example)

//        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract) {
//            it?.let {
//                imageView.setImageURI(it)
//            }
//        }

        findViewById<Button>(R.id.button).setOnClickListener {
//            cropActivityResultLauncher.launch(null)

            val intent = Intent(Intent.ACTION_PICK, null).apply {
                type = "image/*"
                action = android.content.Intent.ACTION_GET_CONTENT
            }

            val chooserIntent = Intent.createChooser(intent, "choose one")

            startForResult.launch(chooserIntent)

        }
    }
}