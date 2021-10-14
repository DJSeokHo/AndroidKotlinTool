package com.swein.androidkotlintool.framework.module.firebase.pagination.service

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.CloudFireStoreWrapper
import com.swein.androidkotlintool.framework.module.firebase.pagination.model.FirebasePaginationItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FirebasePaginationService {

    suspend fun fetch(orderBy: String, offset: DocumentSnapshot? = null, limit: Long, tag: String = ""): QuerySnapshot = withContext(Dispatchers.IO) {

        return@withContext CloudFireStoreWrapper.select(
            collectionPath =  "TEST_PATH",
            orderBy = orderBy,
            offset = offset,
            limit = limit,
            conditionMap = if (tag == "") {
                null
            }
            else {
                mutableMapOf<String, Any>().apply {
                    put(FirebasePaginationItemModel.TAG_KEY, tag)
                }
            }
        )
    }

}