package com.swein.androidkotlintool.main.designpattern


interface Fish {
    fun info()
}

class FryFish(private val property: String): Fish {
    override fun info() {
        println("fry fish is $property")
    }
}

class BBQFish(private val property: String): Fish {
    override fun info() {
        println("BBQ fish is $property")
    }
}

interface FishFactory {

    fun makeFishFood(): Fish
}

class FryFishFactory: FishFactory {

    override fun makeFishFood(): Fish {
        return FryFish("crispy")
    }
}

class BBQFishFactory: FishFactory {
    override fun makeFishFood(): Fish {
        return BBQFish("yummy")
    }
}

fun main() {

    val fryFish = FryFishFactory().makeFishFood()
    fryFish.info()

    val bbqFish = BBQFishFactory().makeFishFood()
    bbqFish.info()

    // I like bbq fish more than fry fish. How about you?
    // see you next video
}