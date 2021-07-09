package com.swein.androidkotlintool.main.examples.rxkotlinlite.observable

import android.os.Handler
import android.os.Looper
import com.swein.androidkotlintool.main.examples.rxkotlinlite.subscriber.Observer
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * U: upper
 * L: lower
 */
class Observable<U>(var eventCreator: EventCreator<U>) {

    companion object {
        fun <U> create(eventCreator: EventCreator<U>): Observable<U> {
            return Observable(eventCreator)
        }

        private val handler = Handler(Looper.getMainLooper())
    }

    private var executor: Executor? = null

    private fun addSubscribe(observer: Observer<in U>) {

        observer.onReady()

        executor?.let {
            it.execute {
                startPassEvent(observer)
            }
        } ?: run {
            startPassEvent(observer)
        }
    }

    private fun startPassEvent(observer: Observer<in U>) {
        try {
            eventCreator.executeEventCreatorAndCall(observer)
        }
        catch (e: Exception) {
            observer.onError(e)
        }
        finally {
            observer.onCompleted()
        }
    }

    fun createEventOnThread(): Observable<U> {
        executor = Executors.newSingleThreadExecutor()
        return this
    }

    fun addSubscribeOnSame(observer: Observer<in U>) {

        addSubscribe(object : Observer<U> {

            override fun onReady() {
                observer.onReady()
            }

            override fun onNext(event: U) {
                observer.onNext(event)
            }

            override fun onCompleted() {
                observer.onCompleted()
            }

            override fun onError(throwable: Throwable) {
                observer.onError(throwable)
            }
        })

    }

    fun addSubscribeOnUI(observer: Observer<in U>) {

        addSubscribe(object : Observer<U> {

            override fun onReady() {
                handler.post {
                    observer.onReady()
                }
            }

            override fun onNext(event: U) {
                handler.post {
                    observer.onNext(event)
                }
            }

            override fun onCompleted() {
                handler.post {
                    observer.onCompleted()
                }
            }

            override fun onError(throwable: Throwable) {
                handler.post {
                    observer.onError(throwable)
                }
            }
        })
    }

    fun <L> map(transformer: Transformer<in U, out L>): Observable<L> {

        return create(object: EventCreator<L> {
            override fun executeEventCreatorAndCall(observer: Observer<in L>) {
                this@Observable.addSubscribe(object : Observer<U> {

                    // if you need
//                    override fun onReady() {
//                        super.onReady()
//                    }

                    override fun onNext(event: U) {
                        // transform the event get from upper level
                        observer.onNext(transformer.executeEventCreatorAndCall(event))
                    }

                    // if you need
//                    override fun onCompleted() {
//                        super.onCompleted()
//                    }

                    override fun onError(throwable: Throwable) {
                        observer.onError(throwable)
                    }
                })
            }
        })
    }

    interface EventCreator<U> {
        /*

        <? extends U>:上界通配符(Upper Bounds Wildcards）
        <? super U>:下界通配符(Lower Bounds Wildcards)

        <? extends U> 适合大量做获取操作的情景，<? super U> 适合大量做添加操作的情景。

        在Kotlin中，我们把那些只能保证读取数据时类型安全的对象叫做生产者，用 out U 标记；
        把那些只能保证写入数据安全时类型安全的对象叫做消费者，用 in U 标记。

        out U 等价于 ? extends U in U 等价于 ? super U 此外, 还有 * 等价于 ?
         */
        fun executeEventCreatorAndCall(observer: Observer<in U>)
    }

    interface Transformer<U, L> {
        fun executeEventCreatorAndCall(eventFromUpper: U): L
    }

}