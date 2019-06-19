package com.swein.androidkotlintool.console.basicgrammar

class BasicGrammar {

    /* 常量 */
    private val TAG: String = "BasicGrammar"

    /* 变量 */
    private var a: Int
    private var b: Int

    /**
     * 构造函数
     */
    constructor(a: Int, b: Int) {
        this.a = a
        this.b = b
    }

    /**
     * 无参返回函数
     */
    fun sum(): Int {
        return a + b
    }

    /**
     * 有参返回函数
     */
    fun sum (a: Int, b: Int): Int {
        return a + b
    }

    /**
     * 字符串变量
     */
    fun info() {
        println("$TAG this is BasicGrammar class")
    }

    fun range() {
        for(i in 0..10) {
            println(i)
        }
        println("----")
        for(i in 0..10 step 2) {
            println(i)
        }
        println("----")
        for(i in 10 downTo 0) {
            println(i)
        }
    }

    /**
     * 重载成员函数
     */
    override fun toString(): String {
//        return super.toString()
        return "a is '$a' b is '$b' and a + b is '${this.sum()}'"
    }
}

fun main(args: Array<String>) {
    var basicGrammar: BasicGrammar = BasicGrammar(1, 2)
    println(basicGrammar.sum())
    println(basicGrammar.sum(2, 4))
    basicGrammar.info()
    println(basicGrammar.toString())

    basicGrammar = BasicGrammar(5, 5)
    println(basicGrammar.sum())
    println(basicGrammar.toString())

    basicGrammar.range()
}