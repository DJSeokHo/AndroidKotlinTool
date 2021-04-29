package com.swein.androidkotlintool.main.jetpackexample.databinding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class TestUser: BaseObservable() {

    @get:Bindable
    var userName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.userName)
        }

    @get:Bindable
    var userAge: Int  = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.userAge)
        }
}