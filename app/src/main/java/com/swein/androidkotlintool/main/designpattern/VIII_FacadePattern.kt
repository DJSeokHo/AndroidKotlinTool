package com.swein.androidkotlintool.main.designpattern

interface NiceFood {

    fun processed()

}

class NiceBurger: NiceFood {

    override fun processed() {
        println("process burger")
    }
}

class NiceSandwich: NiceFood {

    override fun processed() {
        println("process sandwich")
    }

}

class NiceSteak: NiceFood {

    override fun processed() {
        println("process steak")
    }

}

// facade class
class NiceKitchen {

    private val burger = NiceBurger()
    private val sandwich = NiceSandwich()
    private val steak = NiceSteak()

    fun processedBurger() {
        burger.processed()
    }

    fun processedSandwich() {
        sandwich.processed()
    }

    fun processedSteak() {
        steak.processed()
    }
}

fun main() {

    val niceKitchen = NiceKitchen()

    niceKitchen.processedBurger()
    niceKitchen.processedSandwich()
    niceKitchen.processedSteak()
}