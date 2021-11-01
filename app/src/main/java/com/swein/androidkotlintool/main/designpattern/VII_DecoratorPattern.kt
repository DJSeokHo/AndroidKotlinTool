package com.swein.androidkotlintool.main.designpattern


class NormalSteak {

    internal var method = ""
    internal var seasoning = ""

    fun ready() {
        println("$method steak${
            if (seasoning == "") {
                ""
            } 
            else { 
                " with $seasoning" 
            }
        } is ready")
    }
}

fun NormalSteak.byMethod(method: String): NormalSteak {
    this.method = method
    return this
}

fun NormalSteak.withSeasoning(seasoning: String): NormalSteak {
    this.seasoning = seasoning
    return this
}

fun main() {

    NormalSteak().ready()

    NormalSteak().byMethod("bbq").withSeasoning("pepper").ready()

    NormalSteak().byMethod("fry").ready()

    NormalSteak().withSeasoning("cheese").ready()
}