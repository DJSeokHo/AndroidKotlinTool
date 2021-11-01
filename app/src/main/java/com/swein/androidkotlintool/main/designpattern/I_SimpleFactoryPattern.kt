package com.swein.androidkotlintool.main.designpattern


interface Chicken {
    fun info()
}

class FryChicken(private val property: String): Chicken {

    override fun info() {
       println("fry chicken is $property")
    }
}

class BBQChicken(private val property: String): Chicken {

    override fun info() {
        println("BBQ chicken is $property")
    }
}

class HotPotChicken(private val property: String): Chicken {

    override fun info() {
        println("Hot Pot chicken is $property")
    }
}


class SimpleChickenFactory {

    companion object {
        fun makeChicken(type: Int, property: String): Chicken {

            return when (type) {
                0 -> {
                    FryChicken(property)
                }
                1 -> {
                    BBQChicken(property)
                }
                else -> {
                    HotPotChicken(property)
                }
            }
        }
    }

}

fun main() {

    val fryChicken = SimpleChickenFactory.makeChicken(0, "crispy")
    fryChicken.info()

    val bbqChicken = SimpleChickenFactory.makeChicken(1, "yummy")
    bbqChicken.info()

    val hotPotChicken = SimpleChickenFactory.makeChicken(2, "peppery")
    hotPotChicken.info()

    // so, which way do you like? fry or bbq or hot pot?
    // I'm going to eat a bbq chicken now, see you next video~
}