package com.swein.androidkotlintool.console.basicgrammar

import com.swein.androidkotlintool.framework.util.log.ILog

/**
 * Kotlin 中没有基础数据类型，只有封装的数据类型
 */
class BasicDataType {

    companion object {
        private const val TAG: String = "BasicDataType"
    }


    private var a: String
    private var b: String

    constructor(a: String, b: String) {
        this.a = a
        this.b = b
    }

    /**
     * 比较值是否相等
     */
    fun compareValue() {
        val isSameValue: Boolean = (a == b)
        println(isSameValue)
    }

    /**
     * 比较地址是否相等
     */
    fun compareAddress() {
        val isSameAddress: Boolean = (a === b)

        ILog.debug(TAG, isSameAddress)
    }

    fun printMultiRowString(string: String) {
        val multiRowString: String = """
            >row1
            >row2
            >row3
            !!! '$' "$" $string
            hahaha
        """.trimIndent()

        ILog.debug(TAG, multiRowString)
    }

    override fun toString(): String {
        return "a is $a, b is $b and a + b is ${a + b}"
    }

}

fun main(args: Array<String>) {

    var basicDataType: BasicDataType?

    basicDataType = BasicDataType("1", "2")
    basicDataType.compareAddress()
    basicDataType.compareValue()

    basicDataType = BasicDataType("1", "1")
    basicDataType.compareValue()
    basicDataType.compareAddress()

    basicDataType.printMultiRowString("parameter string")
}