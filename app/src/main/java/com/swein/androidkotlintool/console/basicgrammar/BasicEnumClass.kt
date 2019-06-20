package com.swein.androidkotlintool.console.basicgrammar

enum class Human {
    MAN, WOMEN
}

enum class People(val peopleName: String) {
    MALE("Seok Ho"),
    FEMALE("Seok Ho Girl")
}

fun main(args: Array<String>) {

    var human: Human = Human.MAN
    println(human)

    var people: People = People.FEMALE
    println("$people ${people.peopleName}")
}