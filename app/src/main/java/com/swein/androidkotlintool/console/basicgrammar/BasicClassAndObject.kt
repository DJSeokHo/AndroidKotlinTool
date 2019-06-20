package com.swein.androidkotlintool.console.basicgrammar

import com.swein.androidkotlintool.framework.util.log.ILog

class BasicClassAndObject {

    companion object {
        private const val TAG: String = "BasicClassAndObject"
    }

    var name: String? = null
        set(value) {
            if(value.equals("1")) {
                field = "haha"
            }
            else {
                field = "hehe"
            }
        }
        get() {
            return field
        }

    var age: Int? = null

    constructor(name: String, age: Int) {
       this.name = name
        this.age = age
    }

    override fun toString(): String {
        return "$name $age"
    }

    fun print() {
        ILog.debug(TAG, toString())
    }
}

/**
 * open 代表该类可以被继承
 */
open class OpenClass {

    companion object {
        private const val TAG: String = "OpenClass"
    }

    /**
     * open 代表该方法可以被子类重写
     */
    open fun openMethod() {
        ILog.debug(TAG, "parent openMethod")
    }
    open fun openMethod(parameter: String) {}

    open fun print() {
        ILog.debug(TAG, "print")
    }
}

interface OpenClassDelegate {

    /**
     * 接口的成员方法默认是 open 的
     * interface 允许方法有默认实现
     * 一个类或者对象可以实现一个或多个接口
     */
    fun print() {
        println("print from OpenClassDelegate")
    }
}

class ImplOpenClass : OpenClass(), OpenClassDelegate {

    companion object {
        private const val TAG: String = "ImplOpenClass"
    }

    override fun openMethod() {
        super.openMethod()
        ILog.debug(TAG, "openMethod")
    }
    override fun openMethod(parameter: String) {
        super.openMethod(parameter)
        ILog.debug(TAG, "openMethod $parameter")
    }

    override fun print() {
        super<OpenClass>.print()
        super<OpenClassDelegate>.print()
    }
}

/**
 * 可以被继承的抽象类
 * 默认具有open修饰
 */
abstract class AbstractClass {

    companion object {
        private const val TAG: String = "AbstractClass"
    }

    abstract fun abstractMethod()
    abstract fun abstractMethod(parameter: String)
}

class ImplAbstractClass : AbstractClass() {

    companion object {
        private const val TAG: String = "ImplAbstractClass"
    }

    override fun abstractMethod() {
        ILog.debug(TAG, "abstractMethod")
    }

    override fun abstractMethod(parameter: String) {
        ILog.debug(TAG, "abstractMethod $parameter")
    }
}

fun main(args: Array<String>) {

    /**
        abstract    // 抽象类
        final       // 类不可继承，默认属性
        enum        // 枚举类
        open        // 类可继承，类默认是final的
        annotation  // 注解类

        private    // 仅在同一个文件中可见
        protected  // 同一个文件中或子类可见
        public     // 所有调用的地方都可见
        internal   // 同一个模块中可见
     */

    var basicClassAndObject: BasicClassAndObject = BasicClassAndObject("2", 2)

    basicClassAndObject.print()

    var abstractClass: AbstractClass = ImplAbstractClass()
    abstractClass.abstractMethod()
    abstractClass.abstractMethod("haha")

    var openClass: OpenClass = ImplOpenClass()
    openClass.openMethod()
    openClass.openMethod("hehe")
    openClass.print()
}