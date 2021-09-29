package com.swein.androidkotlintool.framework.module.firebase.storage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.thread.ThreadUtility
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream


object FirebaseStorageManager {

    enum class StorageFileType {
        FILE, BYTE, STREAM
    }

    private const val TAG = "FirebaseStorageManager"
    const val FILE_STORAGE_DOMAIN = "gs://androidkotlintool.appspot.com"
    const val MEMBER_IMAGE_FOLDER = "Member/"

    suspend fun uploadImage(uploadPath: String,
                    fileUri: Uri,
                    fileName: String,
                    onSuccess: (imageUrl: String) -> Unit, onFailure: () -> Unit) {

        ThreadUtility.startThread {

            val storageRef: StorageReference =  FirebaseStorage.getInstance(uploadPath).reference
            val storageReference: StorageReference = storageRef.child(fileName)

            val uploadTask: UploadTask = storageReference.putFile(fileUri)

            uploadTask.addOnFailureListener {
                ILog.debug(TAG, "${it.message}")
            }.addOnSuccessListener {
                ILog.debug(TAG, "success ${storageReference.downloadUrl}")
                it.task.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        onFailure()
                    }

                    return@Continuation storageReference.downloadUrl
                }).addOnCompleteListener { uriTask ->
                    if (uriTask.isSuccessful) {
                        val downloadUri = uriTask.result
                        onSuccess(downloadUri.toString())
                    }
                    else {
                        onFailure()
                    }
                }
            }.addOnFailureListener {
                onFailure()
            }

        }
    }

    fun uploadImage(uploadPath: String,
                    type: StorageFileType,
                    filePath: String,
                    fileName: String,
                    onSuccess: (imageUrl: String) -> Unit, onFailure: () -> Unit) {

        ThreadUtility.startThread {

            val storageRef: StorageReference =  FirebaseStorage.getInstance(uploadPath).reference
            val storageReference: StorageReference = storageRef.child(fileName)

            val uploadTask: UploadTask = when (type) {

                StorageFileType.FILE -> {
                    storageReference.putFile(Uri.fromFile(File(filePath)))
                }

                StorageFileType.STREAM -> {
                    storageReference.putStream(FileInputStream(File(filePath)))
                }

                StorageFileType.BYTE -> {
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    BitmapFactory.decodeFile(filePath).compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)
                    storageReference.putBytes(byteArrayOutputStream.toByteArray())
                }
            }

            uploadTask.addOnFailureListener {
                ILog.debug(TAG, "${it.message}")
            }.addOnSuccessListener {
                ILog.debug(TAG, "success ${storageReference.downloadUrl}")
                it.task.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        onFailure()
                    }

                    return@Continuation storageReference.downloadUrl
                }).addOnCompleteListener { uriTask ->
                    if (uriTask.isSuccessful) {
                        val downloadUri = uriTask.result
                        onSuccess(downloadUri.toString())
                    }
                    else {
                        onFailure()
                    }
                }
            }.addOnFailureListener {
                onFailure()
            }

        }
    }

    fun deleteImage(uploadPath: String, fileCloudPath: String, onSuccess: () -> Unit) {
        val storageRef: StorageReference =  FirebaseStorage.getInstance(uploadPath).reference
        val storageReference: StorageReference = storageRef.child(fileCloudPath)

        storageReference.delete().addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            it.printStackTrace()
            ILog.debug(TAG, "${it.message}")
        }
    }
}