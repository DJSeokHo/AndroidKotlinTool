package com.swein.androidkotlintool.framework.module.firebase.cloudfirestore

import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.swein.androidkotlintool.framework.util.log.ILog
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object CloudFireStoreWrapper {

    suspend fun replace(collectionPath: String, documentPath: String, map: MutableMap<String, Any>): Void {

        return suspendCoroutine { continuation ->

            Firebase.firestore.collection(collectionPath).document(documentPath).set(map)
                .addOnSuccessListener {
                    continuation.resume(it)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    suspend fun select(collectionPath: String, conditionMap: MutableMap<String, Any>? = null): QuerySnapshot {

        return suspendCoroutine { continuation ->
            val collectionReference = Firebase.firestore.collection(collectionPath)

            var query = collectionReference.limit(1)

            conditionMap?.let {
                it.forEach { map ->
                    query = collectionReference.whereEqualTo(map.key, map.value)
                }
            }

            query.get()
                .addOnSuccessListener {
                    continuation.resume(it)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }
}