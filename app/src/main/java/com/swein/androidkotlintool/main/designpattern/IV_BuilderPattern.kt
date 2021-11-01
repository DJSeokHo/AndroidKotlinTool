package com.swein.androidkotlintool.main.designpattern


class GreatBurger {

    private val foodList = mutableListOf<String>()
    private var price = 0

    fun addFood(food: String, price: Int) {
        foodList.add(food)
        this.price += price
    }

    fun build() {

        foodList.add(0, "top bread")
        foodList.add("bottom bread")
    }

    fun info() {

        println("great burger is:")

        for (food in foodList) {
            println(food)
        }

        println("great burger price is $price")
    }
}

fun main() {

    val greatBurger = GreatBurger().apply {
        addFood("cheese", 3)
        addFood("steak", 10)
        addFood("tomato", 1)
        addFood("onion", 2)
        addFood("cheese", 3)
        addFood("egg", 2)
        build()
    }

    greatBurger.info()

    // the burger is great, but I will remove the tomato. Do you like tomato in the burger?
    // see you next video
}