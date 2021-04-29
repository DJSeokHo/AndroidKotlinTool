package com.swein.androidkotlintool.main.jetpackexample.databinding

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

/**
ObservableBoolean
ObservableByte
ObservableChar
ObservableShort
ObservableInt
ObservableLong
ObservableFloat
ObservableDouble
ObservableParcelable
 */
class ObservableFieldBean {
    var name = ObservableField<String>()
    var age = ObservableInt()
}