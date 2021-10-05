package com.swein.androidkotlintool.framework.module.firebase.storage.demo

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.firebase.storage.FirebaseStorageManager
import com.swein.androidkotlintool.framework.util.glide.SHGlide
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.systemphotopicker.SystemPhotoPickManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FirebaseStorageActivity : AppCompatActivity() {

    private val systemPhotoPickManager = SystemPhotoPickManager(this)

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_firebase_storage)

        SHGlide.setImage(imageView, url = "https://firebasestorage.googleapis.com/v0/b/androidkotlintool.appspot.com/o/Member%2Ftest.jpg?alt=media&token=387be125-3b91-46b2-92aa-d062a6d423fb")

        imageView.setOnClickListener {

            systemPhotoPickManager.requestPermission {

                it.selectPicture { uri ->

                    lifecycleScope.launch(Dispatchers.Main) {

                        val result = async {
                            FirebaseStorageManager.uploadImage(
                                uri = uri,
                                folderName = FirebaseStorageManager.MEMBER_IMAGE_FOLDER,
                                fileName = "test.jpg"
                            )
                        }

                        val imageUrl = result.await()
                        ILog.debug("???", imageUrl)
                        SHGlide.setImage(imageView, url = imageUrl)
                    }

                }

//                it.selectPathPicture { filePath ->
//                    ILog.debug("???", filePath)
//
//
//
////                    FirebaseStorageManager.uploadImage(
////                        uploadPath = FirebaseStorageManager.FILE_STORAGE_DOMAIN,
////                        type = FirebaseStorageManager.StorageFileType.FILE,
////                        filePath = FirebaseStorageManager.MEMBER_IMAGE_FOLDER,
////                        fileName = "test.jpg",
////                        onSuccess = { imageUrl ->
////                            SHGlide.setImage(imageView, url = imageUrl)
////                        },
////                        onFailure = {
////                            Toast.makeText(this@FirebaseStorageActivity, "error", Toast.LENGTH_SHORT).show()
////                        }
////                    )
//                }

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