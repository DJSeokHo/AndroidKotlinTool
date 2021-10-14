package com.swein.androidkotlintool.framework.module.firebase.pagination.model


data class FirebasePaginationItemModel(
    var title: String = "",
    var message: String = "",
    var createDate: String = "",
    var tag: String = ""
) {

    companion object {
        const val TITLE_KEY = "TITLE"
        const val MESSAGE_KEY = "MESSAGE"
        const val CREATE_DATE_KEY = "CREATE_DATE"
        const val TAG_KEY = "TAG_KEY"
    }

    fun to(): MutableMap<String, Any> {

        val map = mutableMapOf<String, Any>()

        map[TITLE_KEY] = title
        map[MESSAGE_KEY] = message
        map[CREATE_DATE_KEY] = createDate
        map[TAG_KEY] = tag

        return map
    }

    fun parsing(map: MutableMap<String, Any>) {
        title = map[TITLE_KEY] as String
        message = map[MESSAGE_KEY] as String
        createDate = map[CREATE_DATE_KEY] as String
        tag = map[TAG_KEY] as String
    }

}