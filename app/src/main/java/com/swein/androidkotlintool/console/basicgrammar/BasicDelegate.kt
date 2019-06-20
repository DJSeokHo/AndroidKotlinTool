package com.swein.androidkotlintool.console.basicgrammar

interface PrintDelegate {
    fun print()
}

class ImplPrintDelegate: PrintDelegate {

    companion object {
        private const val TAG = "ImplPrintDelegate"
    }

    constructor() {}

    override fun print() {
        println("print of ImplPrintDelegate")
    }
}

class SomeClass {

    companion object {
        private const val TAG = "SomeClass"
    }

    var printDelegate: PrintDelegate? = null

    constructor() {}

    /**
     * 传统设置委托方式
     */
    fun setDelegate(printDelegate: PrintDelegate) {
        this.printDelegate = printDelegate
    }

    fun print() {
        printDelegate?.print()
    }
}

/**
 * Kotlin 优雅设置委托方式
 * 通过关键字 by 建立委托类
 */
class SomeKotlinClass(printDelegate: PrintDelegate) : PrintDelegate by printDelegate {

    override fun print() {
        println("print of SomeKotlinClass")
    }
}

fun main(args: Array<String>) {

    /* 实现委托 */
    var printDelegate: PrintDelegate = ImplPrintDelegate()

    /* 设置委托方式1 */
    var someClass: SomeClass = SomeClass()
    someClass.setDelegate(printDelegate)
    someClass.print()


    /* 设置委托方式2 优雅 */
    var someKotlinClass: SomeKotlinClass = SomeKotlinClass(printDelegate)
    someKotlinClass.print()

    /* 设置委托方式3 更优雅 */
    SomeKotlinClass(printDelegate).print()
}