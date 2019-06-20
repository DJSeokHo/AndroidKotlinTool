package com.swein.androidkotlintool.console.basicgrammar

/**
 * 接口的成员方法允许有默认实现
 * 接口的成员变量只能是抽象的，必须重写
 */
interface BasicInterfaceDelegate {
    var name: String

    fun printName() {
       println(name)
    }

    fun printName(name: String) {
        this.name = name
        println("name is $name")
    }
}

class BasicInterfaceImpl: BasicInterfaceDelegate {

    override var name: String = ""

    companion object {
        private const val TAG: String = "BasicInterfaceImpl"
    }

    constructor(name: String) {
        this.name = name
    }

    override fun printName() {
        super.printName()
    }

    override fun printName(name: String) {
        super.printName(name)
    }

    /* 所有类都继承自 Any, 默认具有这三种方法 */
    override fun toString(): String {
        return super.toString()
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
    /* 所有类都继承自 Any, 默认具有这三种方法 */
}

/**
 * 扩展函数
 * 若扩展函数和成员函数一致，则使用该函数时，会优先使用成员函数
 */
fun BasicInterfaceImpl.setName(name: String) {
    this.name = name
}

fun main(args: Array<String>) {

    var basicInterfaceImpl: BasicInterfaceImpl = BasicInterfaceImpl("Seok Ho")
    basicInterfaceImpl.printName()
    println(basicInterfaceImpl.name)

    basicInterfaceImpl.printName("haha")
    basicInterfaceImpl.printName()
    println(basicInterfaceImpl.name)

    /* 扩展函数 */
    basicInterfaceImpl.setName("Seok Ho")
    basicInterfaceImpl.printName()
    println(basicInterfaceImpl.name)
}