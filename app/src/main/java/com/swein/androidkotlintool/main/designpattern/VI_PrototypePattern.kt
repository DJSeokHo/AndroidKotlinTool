package com.swein.androidkotlintool.main.designpattern

data class PrototypeBreakfast(
    val basicFood: String = "salad",
    val basicDrink: String = "water"
) {
    var food: String = ""
    var drink: String = ""

    fun info() {
        println("$basicFood $basicDrink $food $drink")
    }
}

fun main() {

    val breakfast = PrototypeBreakfast()

    println("my breakfast")
    val myBreakFast = breakfast.copy()
    myBreakFast.food = "burger"
    myBreakFast.drink = "cola"
    myBreakFast.info()

    println("your breakfast")
    val yourBreakFast = breakfast.copy()
    yourBreakFast.food = "sandwich"
    yourBreakFast.drink = "coffee"
    yourBreakFast.info()
}