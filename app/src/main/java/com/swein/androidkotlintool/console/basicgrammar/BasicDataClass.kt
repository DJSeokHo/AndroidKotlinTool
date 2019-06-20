package com.swein.androidkotlintool.console.basicgrammar


data class User(val name: String, val age: Int)

fun main(args: Array<String>) {
    val me = User(name = "Seok Ho", age = 32)
    println(me)

    val youngMe = me.copy(name = "Seok Ho", age = 30)
    println(youngMe)

    println(me)
    println(youngMe)
}