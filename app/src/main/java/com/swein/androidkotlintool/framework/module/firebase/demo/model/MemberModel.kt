package com.swein.androidkotlintool.framework.module.firebase.demo.model

class MemberModel {

    companion object {

        const val CLOUD_DB_PATH = "MEMBER_TABLE"

        const val UUID_KEY = "UUID"
        const val LOGIN_SECRET_TOKEN_KEY = "LOGIN_SECRET_TOKEN"
        const val MEMBER_ID_KEY = "MEMBER_ID"
        const val EMAIL_KEY = "EMAIL"
        const val PROVIDER_ID_KEY = "PROVIDER_ID"
        const val PROVIDER_KEY = "PROVIDER"
        const val PHONE_KEY = "PHONE"
        const val NAME_KEY = "NAME"
        const val BIRTH_KEY = "BIRTH"
        const val GENDER_KEY = "GENDER"
        const val NICKNAME_KEY = "NICKNAME"
        const val PROFILE_IMAGE_URL_KEY = "PROFILE_IMAGE_URL"
        const val PROFILE_IMAGE_FILE_CLOUD_PATH_KEY = "PROFILE_IMAGE_FILE_CLOUD_PATH"
        const val PUSH_TOKEN_KEY = "PUSH_TOKEN_KEY"
        const val CREATE_DATE_KEY = "CREATE_DATE"
        const val MODIFY_DATE_KEY = "MODIFY_DATE"
        const val CREATE_BY_KEY = "CREATE_BY"
        const val MODIFY_BY_KEY = "MODIFY_BY"

    }

    var documentId = ""

    var uuId = ""
    var loginSecretToken = ""
    var memberId = ""
    var email = ""
    var providerId = ""
    var provider = ""
    var phone = ""
    var name = ""
    var birth = ""
    var gender = -1L // 1: male, 0: female
    var nickname = ""
    var profileImageUrl = ""
    var profileImageFileCloudPath = ""
    var pushToken = ""
    var createDate = ""
    var modifyDate = ""
    var createBy = ""
    var modifyBy = ""

    fun to(): MutableMap<String, Any> {

        val map = mutableMapOf<String, Any>()

        map[UUID_KEY] = uuId
        map[LOGIN_SECRET_TOKEN_KEY] = loginSecretToken
        map[MEMBER_ID_KEY] = memberId
        map[EMAIL_KEY] = email
        map[PROVIDER_ID_KEY] = providerId
        map[PROVIDER_KEY] = provider
        map[PHONE_KEY] = phone
        map[NAME_KEY] = name
        map[BIRTH_KEY] = birth
        map[GENDER_KEY] = gender
        map[NICKNAME_KEY] = nickname
        map[PROFILE_IMAGE_URL_KEY] = profileImageUrl
        map[PROFILE_IMAGE_FILE_CLOUD_PATH_KEY] = profileImageFileCloudPath
        map[PUSH_TOKEN_KEY] = pushToken
        map[CREATE_DATE_KEY] = createDate
        map[MODIFY_DATE_KEY] = modifyDate
        map[CREATE_BY_KEY] = createBy
        map[MODIFY_BY_KEY] = modifyBy

        return map
    }

    fun parsing(map: MutableMap<String, Any>) {
        uuId = map[UUID_KEY] as String
        loginSecretToken = map[LOGIN_SECRET_TOKEN_KEY] as String
        memberId = map[MEMBER_ID_KEY] as String
        email = map[EMAIL_KEY] as String
        providerId = map[PROVIDER_ID_KEY] as String
        provider = map[PROVIDER_KEY] as String
        phone = map[PHONE_KEY] as String
        name = map[NAME_KEY] as String
        birth = map[BIRTH_KEY] as String
        gender = map[GENDER_KEY] as Long
        nickname = map[NAME_KEY] as String
        profileImageUrl = map[PROFILE_IMAGE_URL_KEY] as String
        profileImageFileCloudPath = map[PROFILE_IMAGE_FILE_CLOUD_PATH_KEY] as String
        pushToken = map[PUSH_TOKEN_KEY] as String
        createDate = map[CREATE_DATE_KEY] as String
        modifyDate = map[MODIFY_DATE_KEY] as String
        createBy = map[CREATE_BY_KEY] as String
        modifyBy = map[MODIFY_BY_KEY] as String
    }
}