package com.swein.androidkotlintool.template.mvp.mvploginexample.presenter

interface ILoginPresenter {

    fun clear()
    fun login(id: String, password: String)
    fun showProgress()
    fun hideProgress()
}