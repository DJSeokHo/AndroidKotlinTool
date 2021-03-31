package com.swein.androidkotlintool.template.mvp.mvploginexample.view

import com.swein.androidkotlintool.template.mvp.mvploginexample.model.IUser

interface ILoginView {

    fun onClear()
    fun onLoginSuccessUpdateNicknameUI(nickname: String)
    fun onLoginSuccessUpdateAgeUI(age: Int)
    fun onShowProgress()
    fun onHideProgress()

}