package com.swein.androidkotlintool.main.designpattern


interface BreakfastBuilder {

    fun makeFood(): String

    fun makeDrink(): String
}

class Breakfast(private val breakfastBuilder: BreakfastBuilder) {

    private var food = ""
    private var drink = ""

    fun makeFood() {
        food = breakfastBuilder.makeFood()
    }

    fun makeDrink() {
        drink = breakfastBuilder.makeDrink()
    }

    fun ready() {
        println("breakfast is $food and $drink")
    }
}



fun main() {

    println("my breakfast")
    val myBreakfast = Breakfast(object : BreakfastBuilder {
        override fun makeFood(): String {
           return "burger"
        }

        override fun makeDrink(): String {
           return "juice"
        }
    })

    myBreakfast.makeFood()
    myBreakfast.makeDrink()
    myBreakfast.ready()

    println("your breakfast")
    val yourBreakfast = Breakfast(object : BreakfastBuilder {
        override fun makeFood(): String {
            return "sandwich"
        }

        override fun makeDrink(): String {
            return "coffee"
        }
    })

    yourBreakfast.makeFood()
    yourBreakfast.makeDrink()
    yourBreakfast.ready()
}