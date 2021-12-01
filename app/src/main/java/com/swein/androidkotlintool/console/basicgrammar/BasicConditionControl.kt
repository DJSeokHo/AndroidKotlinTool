package com.swein.androidkotlintool.console.basicgrammar

import com.swein.androidkotlintool.framework.utility.debug.ILog

class BasicConditionControl {

    companion object {
        private const val TAG: String = "BasicConditionControl"
    }


    constructor(){}

    fun ifMethod() {

        val max: Boolean = 3 > 2

        ILog.debug(TAG, max)
    }

    fun ifInRange() {
        val x: Int?
        x = 5
        if (x in 1..8) {
            ILog.debug(TAG, "x in range")
        }
    }

    /**
     * when 类似 switch
     *
     * 最后的else 类似于 default
     */
    fun whenMethod() {
        val x: Int?
        x = 3

        when(x) {

            in 4..6 -> ILog.debug(TAG, "x in 4 - 6")

            in 3..6 -> ILog.debug(TAG, "x in 3 - 6")

            else -> ILog.debug(TAG, "none")
        }
    }

    /**
     * when 类似 switch
     *
     * 最后的else 类似于 default
     */
    fun whenMethodWithReturn(x: Int): Int {

        when(x) {

            in 4..6 -> {
                ILog.debug(TAG, "x in 4 - 6")
                return 1
            }

            in 3..6 -> {
                ILog.debug(TAG, "x in 3 - 6")
                return 2
            }

            else ->{
                ILog.debug(TAG, "none")
                return 3
            }
        }
    }
}

fun main(args: Array<String>) {

    var basicConditionControl: BasicConditionControl?

    basicConditionControl = BasicConditionControl()

    basicConditionControl.ifMethod()
    basicConditionControl.ifInRange()
    basicConditionControl.whenMethod()

    ILog.debug("when ", basicConditionControl.whenMethodWithReturn(3))

}