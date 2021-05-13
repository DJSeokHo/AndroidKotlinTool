package com.swein.androidkotlintool.framework.module.firebase.clouddatabase

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object CloudDBManager {

    interface InsertDelegate {
        fun onSuccess(documentReference: DocumentReference?, map: MutableMap<String, Any>)
        fun onFailure(e: Exception?)
    }

    interface UpdateDelegate {
        fun onSuccess(documentId: String, map: MutableMap<String, Any>)
        fun onFailure(e: Exception?)
    }

    interface DeleteDelegate {
        fun onSuccess(documentId: String)
        fun onFailure(e: Exception?)
    }

    interface SelectDelegate {
        fun onSuccess(list: MutableList<MutableMap<String, Any>>, documentIdList: MutableList<String>, documentSnapshot: DocumentSnapshot?)
        fun onFailure(e: Exception?)
        fun onEmpty()
    }

    fun select(
        collectionPath: String,
        offset: DocumentSnapshot? = null, limit: Long = 10,
        orderByCol: String? = null,
        isDesc: Boolean = true,
        conditionMap: MutableMap<String, Any>? = null,
        delegate: SelectDelegate
    ) {

        val collectionReference = Firebase.firestore.collection(collectionPath)
        var query = collectionReference.limit(limit)

        offset?.let {
            query = query.startAfter(offset)
        }

        orderByCol?.let {
            query = query.orderBy(orderByCol, if (isDesc) {
                    Query.Direction.DESCENDING
                }
                else {
                    Query.Direction.ASCENDING
                }
            )
        }

        conditionMap?.let {
            it.forEach { map ->
                query = query.whereEqualTo(map.key, map.value)
            }
        }

        query.get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    delegate.onEmpty()
                    return@addOnSuccessListener
                }

                val documents = it.documents

                val lastVisibleItem = documents[it.size() - 1]

                val list = mutableListOf<MutableMap<String, Any>>()
                val documentIdList = mutableListOf<String>()
                for (document in documents) {
                    list.add(document.data as MutableMap<String, Any>)
                    documentIdList.add(document.id)
                }

                delegate.onSuccess(list, documentIdList, lastVisibleItem)
            }
            .addOnFailureListener {
                delegate.onFailure(it)
            }

    }

    fun insert(collectionPath: String, map: MutableMap<String, Any>, delegate: InsertDelegate) {
        Firebase.firestore.collection(collectionPath).add(map)
            .addOnSuccessListener {
                delegate.onSuccess(it, map)
            }
            .addOnFailureListener {
                delegate.onFailure(it)
            }
    }

    fun update(
        collectionPath: String,
        documentId: String,
        map: MutableMap<String, Any>,
        delegate: UpdateDelegate
    ) {
        Firebase.firestore.collection(collectionPath).document(documentId).update(map)
            .addOnSuccessListener {
                delegate.onSuccess(documentId, map)
            }
            .addOnFailureListener {
                delegate.onFailure(it)
            }
    }

    fun delete(collectionPath: String, documentId: String, delegate: DeleteDelegate) {
        Firebase.firestore.collection(collectionPath).document(documentId)
            .delete()
            .addOnSuccessListener {
                delegate.onSuccess(documentId)
            }
            .addOnFailureListener {
                delegate.onFailure(it)
            }
    }

}