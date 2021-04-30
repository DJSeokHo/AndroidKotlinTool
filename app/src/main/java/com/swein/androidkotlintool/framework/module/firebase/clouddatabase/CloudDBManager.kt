package com.swein.androidkotlintool.framework.module.firebase.clouddatabase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.swein.androidkotlintool.framework.util.log.ILog

object CloudDBManager {

    private const val TAG = "CloudDBManager"

    fun select() {

    }

    fun insert(collectionPath: String, map: MutableMap<String, Any>) {
        Firebase.firestore.collection(collectionPath).add(map)
            .addOnSuccessListener {
                ILog.debug(TAG, "DocumentSnapshot added with ID: ${it.id}")
            }
            .addOnFailureListener {
                ILog.debug(TAG, "Error adding document ${it.message}")
            }
    }

    fun update(collectionPath: String, documentId: String, map: MutableMap<String, Any>) {
        Firebase.firestore.collection(collectionPath).document(documentId).update(map)
            .addOnSuccessListener {
                ILog.debug(TAG, "documentId DocumentSnapshot updated")
            }
            .addOnFailureListener {
                ILog.debug(TAG, "Error adding document ${it.message}")
            }
    }

    fun delete(collectionPath: String, documentId: String) {
        Firebase.firestore.collection(collectionPath).document(documentId)
            .delete()
            .addOnSuccessListener {
                ILog.debug(TAG, "$documentId DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener {
                ILog.debug(TAG, "Error deleting document ${it.message}")
            }
    }

}