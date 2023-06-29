package com.swein.androidkotlintool.advanceexamples.threads

class SynchronizedDemo3: TestDemo {

    private var x = 0
    private var y = 0

    private var name = ""

    // 通过不同的锁可以同时监听两个不相关值的原子性和同步性。
    private val monitorForXAndY = Object()
    private val monitorForName = Object()

    private fun count(value: Int) {

//        synchronized(this) 用自身对象作为锁，也就是一个锁监听所有加了同步保护的方法。
        synchronized(monitorForXAndY) {
            x = value
            y = value
        }
    }

    private fun minus(delta: Int) {

        synchronized(monitorForXAndY) {
            x -= delta
            y -= delta
        }

    }

    private fun setName(name: String) {
        synchronized(monitorForName) {
            this.name = name
        }
    }

    // 死锁例子
    // 因为要求顺序执行，用了嵌套锁，某一时刻，两个 monitor都被占用了，两个锁都无法执行第二步，因为被对方互相持有。这就是死锁。要特别注意。
    private fun setNumberAndName() {
        synchronized(monitorForXAndY) {
            x = 5
            y = 6

            // 第二步
            synchronized(monitorForName) {
                this.name = "name1"
            }
        }
    }
    private fun setNameAndNumber() {
        synchronized(monitorForName) {
            this.name = "name"

            // 第二步
            synchronized(monitorForXAndY) {
                x = 1
                y = 2
            }
        }
    }
    // 死锁例子

    override fun runTest() {
    }
}