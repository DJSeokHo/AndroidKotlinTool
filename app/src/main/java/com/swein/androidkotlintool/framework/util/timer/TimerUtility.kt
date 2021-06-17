package com.swein.androidkotlintool.framework.util.timer

import android.os.CountDownTimer
import com.swein.androidkotlintool.framework.util.thread.ThreadUtility
import java.util.*

class TimerUtility {

    companion object {

        /**
         *
         * @param onTick
         * @param onFinish
         * @param second count down time (unit second)
         * @param interval count down interval (unit second)
         * @return
         */
        fun countDownTimerTask(onTick: Runnable , onFinish: Runnable, second: Int, interval: Int): CountDownTimer {

            val countDownTimer: CountDownTimer = object: CountDownTimer((second * 1000).toLong(), (interval * 1000).toLong()) {

                override fun onFinish() {
                    onTick.run()
                }

                override fun onTick(millisUntilFinished: Long) {
                    onFinish.run()
                }
            }

            countDownTimer.start()

            return countDownTimer
        }

        fun stopCountDownTimerTask(countDownTimer: CountDownTimer) {

            countDownTimer.cancel()
        }

        fun createTimerTask(delay: Long, period: Long, runnable: Runnable ): Timer {

            val timer = Timer()
            val task: TimerTask  = object: TimerTask() {
                override fun run() {

                    ThreadUtility.startUIThread(0, Runnable {
                        runnable.run()
                    })
                }
            }

            timer.scheduleAtFixedRate(task, delay, period)

            return timer
        }

    }

}