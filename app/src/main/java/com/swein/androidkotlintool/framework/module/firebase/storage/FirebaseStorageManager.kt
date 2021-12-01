package com.swein.androidkotlintool.framework.module.firebase.storage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.framework.utility.thread.ThreadUtility
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


object FirebaseStorageManager {

    enum class StorageFileType {
        FILE, BYTE, STREAM
    }

    private const val TAG = "FirebaseStorageManager"
    const val FILE_STORAGE_DOMAIN = "gs://androidkotlintool.appspot.com"
    const val MEMBER_IMAGE_FOLDER = "Member/"

    suspend fun uploadImage(uri: Uri, folderName: String, fileName: String): String {

        return suspendCoroutine { continuation ->

            val storageRef = Firebase.storage(FILE_STORAGE_DOMAIN).reference
            val storageReference: StorageReference = storageRef.child("$folderName/$fileName")

            val uploadTask: UploadTask = storageReference.putFile(uri)

            uploadTask.addOnFailureListener {

                continuation.resumeWithException(it)

            }.addOnSuccessListener {

                ILog.debug(TAG, "success ${storageReference.downloadUrl}")

                it.task.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let { exception ->
                            continuation.resumeWithException(exception)
                        }

                    }
                    return@Continuation storageReference.downloadUrl

                }).addOnCompleteListener { uriTask ->

                    if (uriTask.isSuccessful) {
                        val downloadUri = uriTask.result
                        continuation.resume(downloadUri.toString())
                    }

                }

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

    suspend fun deleteImage(filePath: String): Void {

        return suspendCoroutine { continuation ->

            val storageRef: StorageReference =  FirebaseStorage.getInstance(FILE_STORAGE_DOMAIN).reference
            val storageReference: StorageReference = storageRef.child(filePath)

            storageReference.delete().addOnSuccessListener {
                continuation.resume(it)
            }.addOnFailureListener {
                it.printStackTrace()
                continuation.resumeWithException(it)
            }

        }

    }
}