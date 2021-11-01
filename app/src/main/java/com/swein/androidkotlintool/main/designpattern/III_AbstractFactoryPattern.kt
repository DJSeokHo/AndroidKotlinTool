package com.swein.androidkotlintool.main.designpattern

interface Coffee {
    fun info()
}

class Americano: Coffee {
    override fun info() {
        println("americano is good")
    }
}

class Espresso: Coffee {
    override fun info() {
        println("espresso is nice")
    }
}

interface Burger {
    fun info()
}

class OnionBurger: Burger {
    override fun info() {
        println("onion burger is delicious")
    }
}

class WagyuSteakBurger: Burger {
    override fun info() {
        println("wagyu steak burger is yummy")
    }
}

interface Sandwich {
    fun info()
}

class MeatSandwich: Sandwich {
    override fun info() {
        println("sandwich need meat")
    }
}

class TomatoSandwich: Sandwich {
    override fun info() {
        println("tomato sandwich is healthy")
    }
}


interface SetMealFactory {
    fun makeCoffee(): Coffee
    fun makeBurger(): Burger
    fun makeSandwich(): Sandwich
}

class MeatSetMealFactory: SetMealFactory {
    override fun makeCoffee(): Coffee {
        return Americano()
    }

    override fun makeBurger(): Burger {
        return WagyuSteakBurger()
    }

    override fun makeSandwich(): Sandwich {
        return MeatSandwich()
    }
}

class VeggieSetMealFactory: SetMealFactory {

    override fun makeCoffee(): Coffee {
        return Espresso()
    }

    override fun makeBurger(): Burger {
        return OnionBurger()
    }

    override fun makeSandwich(): Sandwich {
        return TomatoSandwich()
    }

}

fun main() {

    println("I like meat")
    var coffee = MeatSetMealFactory().makeCoffee()
    coffee.info()

    var burger = MeatSetMealFactory().makeBurger()
    burger.info()

    var sandwich = MeatSetMealFactory().makeSandwich()
    sandwich.info()


    println("I like veggie")
    coffee = VeggieSetMealFactory().makeCoffee()
    coffee.info()

    burger = VeggieSetMealFactory().makeBurger()
    burger.info()

    sandwich = VeggieSetMealFactory().makeSandwich()
    sandwich.info()

    // so which set do you like? I just like eating wagyu steak
    // see you next video
}