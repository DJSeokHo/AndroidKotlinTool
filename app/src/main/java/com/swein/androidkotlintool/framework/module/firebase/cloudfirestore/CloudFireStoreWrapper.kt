package com.swein.androidkotlintool.framework.module.firebase.cloudfirestore

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

    suspend fun select(collectionPath: String, orderBy: String? = null, offset: DocumentSnapshot? = null, limit: Long = 1, conditionMap: MutableMap<String, Any>? = null): QuerySnapshot {

        return suspendCoroutine { continuation ->
            val collectionReference = Firebase.firestore.collection(collectionPath)

            var query: Query? = null

            // 1. set order by, if need
            orderBy?.let {

                query = collectionReference.orderBy(it, Query.Direction.DESCENDING)
            }

            // 2. set offset, if need
            offset?.let { offset ->

                // check query is null or not
                query?.let {
                    query = it.startAfter(offset)
                } ?: run {
                    query = collectionReference.startAfter(offset)
                }
            }

            // 3. set limit, if need
            query?.let {

                // check query is null or not
                query = it.limit(limit)
            } ?: run {
                query = collectionReference.limit(limit)
            }

            // 4. condition
            // this condition may ask to create a index
            // I'll show you how to create index when we testing it
            conditionMap?.let {
                it.forEach { map ->
                    query = query?.whereEqualTo(map.key, map.value)
                }
            }

            // do not change the query order
            // must be 1 - order by, 2 - offset, 3 - limit, 4 - condition

            query?.get()
                ?.addOnSuccessListener {
                    continuation.resume(it)
                }
                ?.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }


    suspend fun update(collectionPath: String, documentPath: String, map: MutableMap<String, Any>): Void {

        return suspendCoroutine { continuation ->

            Firebase.firestore.collection(collectionPath).document(documentPath).update(map)
                .addOnSuccessListener {
                    continuation.resume(it)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    suspend fun delete(collectionPath: String, documentPath: String): Void {

        return suspendCoroutine { continuation ->

            Firebase.firestore.collection(collectionPath).document(documentPath).delete()
                .addOnSuccessListener {
                    continuation.resume(it)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }
}