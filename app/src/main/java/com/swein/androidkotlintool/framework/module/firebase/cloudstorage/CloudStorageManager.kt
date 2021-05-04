package com.swein.androidkotlintool.framework.module.firebase.cloudstorage

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.swein.androidkotlintool.framework.util.log.ILog
import java.io.File

object CloudStorageManager {

    private const val TAG = "CloudStorageManager"

    fun uploadImage(uploadPath: String, filePath: String, fileName: String) {


        val storageRef: StorageReference =  FirebaseStorage.getInstance(uploadPath).reference

        val file = Uri.fromFile(File(filePath))
        val storageReference: StorageReference = storageRef.child(fileName)
        val uploadTask: UploadTask = storageReference.putFile(file)

        uploadTask.addOnFailureListener {
            ILog.debug(TAG, "${it.message}")
        }.addOnSuccessListener {
            ILog.debug(TAG, "success ${storageReference.downloadUrl}")
            it.task.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    ILog.debug(TAG, "error")
                }

                return@Continuation storageReference.downloadUrl
            }).addOnCompleteListener { uriTask ->
                if (uriTask.isSuccessful) {
                    ILog.debug(TAG, "task success")
                    val downloadUri = uriTask.result
                    ILog.debug(TAG, downloadUri.toString())
                }
                else {
                    ILog.debug(TAG, "error")
                }
            }
        }

//        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> {
//            if (!it.isSuccessful) {
//                ILog.debug(TAG, "error")
//            }
//
//            return@Continuation storageReference.downloadUrl
//        }).addOnCompleteListener {
//            if (it.isSuccessful) {
//                ILog.debug(TAG, "task success")
//                val downloadUri = it.result
//                ILog.debug(TAG, downloadUri.toString())
//            }
//            else {
//                ILog.debug(TAG, "error")
//            }
//        }

//        uploadTask.continueWith {
//            if (!it.isSuccessful) {
//
//            }
//
//            storageReference.downloadUrl
//            ILog.debug(TAG, "??? ${storageReference.downloadUrl}")
//        }.addOnCompleteListener {
//            if (it.isSuccessful) {
//                ILog.debug(TAG, "success")
//                val downloadUri = it.result
//                ILog.debug(TAG, "downloadUri is ${downloadUri.toString()}")
//            }
//            else {
//                ILog.debug(TAG, "error")
//            }
//        }

//        uploadTask.addOnFailureListener(OnFailureListener { exception -> exception.printStackTrace() })
//            .addOnSuccessListener(
//                OnSuccessListener<Any?> {
//                    ILog.debug(TAG, "success")
////                    fcsManagerDelegate.onUploadSuccess()
//                })
//        uploadTask.continueWithTask(Continuation<Any?, Task<Uri?>?> { task ->
//            if (!task.isSuccessful) {
////                fcsManagerDelegate.onError()
//            }
//
//            // Continue with the task to get the download URL
//            storageReference.getDownloadUrl()
//        }).addOnCompleteListener(OnCompleteListener<Uri> { task ->
//            if (task.isSuccessful) {
//                ILog.debug(TAG, "task success")
//                val downloadUri = task.result
//                ILog.debug(TAG, downloadUri)
////                fcsManagerDelegate.onSuccess(downloadUri.toString())
//            } else {
//                //  failures
////                fcsManagerDelegate.onError()
//            }
//        })
    }
}