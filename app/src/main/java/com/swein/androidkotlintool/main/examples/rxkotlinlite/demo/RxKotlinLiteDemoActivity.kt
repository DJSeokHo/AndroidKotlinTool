package com.swein.androidkotlintool.main.examples.rxkotlinlite.demo

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.rxkotlinlite.observable.Observable
import com.swein.androidkotlintool.main.examples.rxkotlinlite.subscriber.Observer

class RxKotlinLiteDemoActivity : AppCompatActivity() {

    private val textView: TextView by lazy {
        findViewById(R.id.textView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_kotlin_lite_demo)

//        test()
//        transformTest()
        threadTest()
    }

    fun test() {

        Observable.create(object: Observable.EventCreator<Int> {

            override fun executeEventCreatorAndCall(observer: Observer<in Int>) {
                for (i in 0 until 10000) {
                    if (i == 9999) {
                        observer.onNext(i / 0)
                    }
                    else {
                        observer.onNext(i)
                    }
                }
            }
        }).addSubscribeOnSame(object: Observer<Int> {

            override fun onReady() {
                ILog.debug("???", "onReady")
            }

            override fun onNext(event: Int) {
                ILog.debug("???", "event $event")
            }

            override fun onCompleted() {
                ILog.debug("???", "onCompleted")
            }

            override fun onError(throwable: Throwable) {
                ILog.debug("???", throwable.message)
            }
        })

    }

    fun transformTest() {
        Observable.create(object: Observable.EventCreator<Int> {

            override fun executeEventCreatorAndCall(observer: Observer<in Int>) {
                for (i in 0 until 10000) {
                    if (i == 9999) {
                        observer.onNext(i / 0)
                    }
                    else {
                        observer.onNext(i)
                    }
                }
            }

        }).map(object: Observable.Transformer<Int, String> {

            override fun executeEventCreatorAndCall(eventFromUpper: Int): String {
                ILog.debug("???", "transform to String")
                return "$eventFromUpper"
            }

        }).map(object: Observable.Transformer<String, Double> {

            override fun executeEventCreatorAndCall(eventFromUpper: String): Double {
                ILog.debug("???", "transform to Double")
                return eventFromUpper.toDouble()
            }

        }).addSubscribeOnSame(object: Observer<Double> {

            override fun onReady() {
                ILog.debug("???", "onReady")
            }

            override fun onNext(event: Double) {
                ILog.debug("???", "event $event")
            }

            override fun onCompleted() {
                ILog.debug("???", "onCompleted")
            }

            override fun onError(throwable: Throwable) {
                ILog.debug("???", throwable.message)
            }
        })
    }

    private fun threadTest() {
        Observable.create(object: Observable.EventCreator<Int> {

            override fun executeEventCreatorAndCall(observer: Observer<in Int>) {
                for (i in 0 until 5) {

                    Thread.sleep(2000)
                    if (i == 4) {
                        observer.onNext(i / 0)
                    }
                    else {
                        observer.onNext(i)
                    }
                }
            }
        }).createEventOnThread()
//            .addSubscribeOnSame(object: Observer<Int> {
            .addSubscribeOnUI(object: Observer<Int> {

            override fun onReady() {
                ILog.debug("???", "onReady")
            }

            override fun onNext(event: Int) {
                ILog.debug("???", "event $event")
                textView.text = "event $event"
            }

            override fun onCompleted() {
                ILog.debug("???", "onCompleted")
                Toast.makeText(this@RxKotlinLiteDemoActivity, "onCompleted", Toast.LENGTH_SHORT).show()
                ILog.debug("???", "toast shown")
                textView.text = "onCompleted"
            }

            override fun onError(throwable: Throwable) {
                ILog.debug("???", throwable.message)
                Toast.makeText(this@RxKotlinLiteDemoActivity, throwable.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}