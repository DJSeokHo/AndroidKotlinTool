package com.swein.androidkotlintool.main.designpattern

abstract class Cooking {

    abstract fun prepareFood()
    abstract fun prepareSeasoning()
    abstract fun cook()
    abstract fun finish()

}

class SteakMode: Cooking() {

    override fun prepareFood() {
        println("prepare steak")
    }

    override fun prepareSeasoning() {
        println("prepare pepper and garlic")
    }

    override fun cook() {
        println("cook steak")
    }

    override fun finish() {
        println("steak is ready")
    }
}

class SandwichMode: Cooking() {

    override fun prepareFood() {
        println("prepare bread and fish")
    }

    override fun prepareSeasoning() {
        println("prepare mayonnaise")
    }

    override fun cook() {
        println("cook sandwich")
    }

    override fun finish() {
        println("fish mayonnaise sandwich is ready")
    }
}

class OneButtonCookingMachine(private val cooking: Cooking): Cooking() {

    fun start() {
        this.prepareFood()
        this.prepareSeasoning()
        this.cook()
        this.finish()
    }

    override fun prepareFood() {
        cooking.prepareFood()
    }

    override fun prepareSeasoning() {
        cooking.prepareSeasoning()
    }

    override fun cook() {
        cooking.cook()
    }

    override fun finish() {
        cooking.finish()
    }

}

fun main() {

    println("I want to eat steak ==============")
    OneButtonCookingMachine(SteakMode()).start()

    println("I want to eat fish sandwich =================")
    OneButtonCookingMachine(SandwichMode()).start()
}