package com.swein.androidkotlintool.main.examples.workmanager

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.work.*
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog


class WorkManagerDemoActivity : FragmentActivity() {

    private lateinit var textViewOrange: TextView
    private lateinit var textViewTeal: TextView

    companion object {
        private const val ORANGE_WORK = "ORANGE_WORK"
        private const val TEAL_WORK = "TEAL_WORK"

        private const val ORANGE_AND_TEAL_WORKS = "ORANGE_AND_TEAL_WORKS"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager_demo)

        textViewOrange = findViewById(R.id.textViewOrange)
        textViewTeal = findViewById(R.id.textViewTeal)

        findViewById<Button>(R.id.button).setOnClickListener {
//            processTogether()
//            processOneByOne()
            finishTogether()
        }

        findViewById<Button>(R.id.buttonCancelOrange).setOnClickListener {
            WorkManager.getInstance(this).cancelAllWorkByTag(ORANGE_WORK)
        }

        findViewById<Button>(R.id.buttonCancelTeal).setOnClickListener {
            WorkManager.getInstance(this).cancelAllWorkByTag(TEAL_WORK)
        }
    }

    private fun processOneByOne() {
        ILog.debug("???", "processOneByOne")

        val testViewOrangeWorkRequest = OneTimeWorkRequestBuilder<ProcessTestViewOrangeWorker>()
            .addTag(ORANGE_WORK)
            .setInputData(createInputData())
            .build()

        val testViewTealWorkRequest = OneTimeWorkRequestBuilder<ProcessTestViewTealWorker>()
            .addTag(TEAL_WORK)
            .setInputData(createInputData())
            .build()

        WorkManager.getInstance(this)
            .beginWith(testViewOrangeWorkRequest)
            .then(testViewTealWorkRequest)
            .enqueue()

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(testViewOrangeWorkRequest.id).observe(this,
            {

                if (WorkInfo.State.RUNNING == it.state && it.progress.hasKeyWithValueOfType<Int>("progress")) {
                    ILog.debug("???", "orange progress ${it.progress.getInt("progress", -1)}% ${it.state.isFinished}")
                }

                if (it.state.isFinished && WorkInfo.State.SUCCEEDED == it.state) {
                    val data = it.outputData.getString("data")
                    ILog.debug("???", "orange result $data ${it.state.isFinished} ${it.state.name}")
                    textViewOrange.text = data
                }
                else if (it.state.isFinished && WorkInfo.State.CANCELLED == it.state) {
                    ILog.debug("???", "orange cancel")
                }
            })

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(testViewTealWorkRequest.id).observe(this,
            {

                if (WorkInfo.State.RUNNING == it.state && it.progress.hasKeyWithValueOfType<Int>("progress")) {
                    ILog.debug("???", "teal progress ${it.progress.getInt("progress", -1)}% ${it.state.isFinished}")
                }

                if (it.state.isFinished && WorkInfo.State.SUCCEEDED == it.state) {
                    val data = it.outputData.getString("data")
                    ILog.debug("???", "teal result $data ${it.state.isFinished} ${it.state.name}")
                    textViewTeal.text = data
                }
                else if (it.state.isFinished && WorkInfo.State.CANCELLED == it.state) {
                    ILog.debug("???", "teal cancel")
                }
            })

    }

    private fun processTogether() {

        val testViewOrangeWorkRequest = OneTimeWorkRequestBuilder<ProcessTestViewOrangeWorker>()
            .addTag(ORANGE_WORK)
            .setInputData(createInputData())
            .build()

        val testViewTealWorkRequest = OneTimeWorkRequestBuilder<ProcessTestViewTealWorker>()
            .addTag(TEAL_WORK)
            .setInputData(createInputData())
            .build()

        WorkManager.getInstance(this).enqueue(listOf(testViewOrangeWorkRequest, testViewTealWorkRequest))

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(testViewOrangeWorkRequest.id).observe(this,
            {

                if (WorkInfo.State.RUNNING == it.state && it.progress.hasKeyWithValueOfType<Int>("progress")) {
                    ILog.debug("???", "orange progress ${it.progress.getInt("progress", -1)}% ${it.state.isFinished}")
                }

                if (it.state.isFinished && WorkInfo.State.SUCCEEDED == it.state) {
                    val data = it.outputData.getString("data")
                    ILog.debug("???", "orange result $data ${it.state.isFinished} ${it.state.name}")
                    textViewOrange.text = data
                }
                else if (it.state.isFinished && WorkInfo.State.CANCELLED == it.state) {
                    ILog.debug("???", "orange cancel")
                }
            })

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(testViewTealWorkRequest.id).observe(this,
            {

                if (WorkInfo.State.RUNNING == it.state && it.progress.hasKeyWithValueOfType<Int>("progress")) {
                    ILog.debug("???", "teal progress ${it.progress.getInt("progress", -1)}% ${it.state.isFinished}")
                }

                if (it.state.isFinished && WorkInfo.State.SUCCEEDED == it.state) {
                    val data = it.outputData.getString("data")
                    ILog.debug("???", "teal result $data ${it.state.isFinished} ${it.state.name}")
                    textViewTeal.text = data
                }
                else if (it.state.isFinished && WorkInfo.State.CANCELLED == it.state) {
                    ILog.debug("???", "teal cancel")
                }
            })
    }

    private fun finishTogether() {

        val testViewOrangeWorkRequest = OneTimeWorkRequestBuilder<ProcessTestViewOrangeWorker>()
            .setInputData(createInputData())
            .build()

        val testViewTealWorkRequest = OneTimeWorkRequestBuilder<ProcessTestViewTealWorker>()
            .setInputData(createInputData())
            .build()

        WorkManager.getInstance(this)
            .beginUniqueWork(ORANGE_AND_TEAL_WORKS, ExistingWorkPolicy.KEEP, listOf(testViewOrangeWorkRequest, testViewTealWorkRequest))
            .enqueue()

        WorkManager.getInstance(this).getWorkInfosForUniqueWorkLiveData(ORANGE_AND_TEAL_WORKS).observe(this, {

            var orangeWorkInfo: WorkInfo? = null
            var tealWorkInfo: WorkInfo? = null
            for (workInfo in it) {

                if (workInfo.id == testViewOrangeWorkRequest.id) {
                    orangeWorkInfo = workInfo
                }
                else if (workInfo.id == testViewTealWorkRequest.id) {
                    tealWorkInfo = workInfo
                }

                if (WorkInfo.State.RUNNING == workInfo.state && workInfo.progress.hasKeyWithValueOfType<Int>("progress")) {
                    ILog.debug("???", "${workInfo.tags} progress ${workInfo.progress.getInt("progress", -1)}% ${workInfo.state.isFinished}")
                }
            }

            if (orangeWorkInfo != null && tealWorkInfo != null) {

                if (WorkInfo.State.SUCCEEDED == orangeWorkInfo.state && WorkInfo.State.SUCCEEDED == tealWorkInfo.state) {
                    textViewOrange.text = orangeWorkInfo.outputData.getString("data")
                    textViewTeal.text = tealWorkInfo.outputData.getString("data")
                }
            }

        })
    }
}

class ProcessTestViewOrangeWorker(context: Context, params: WorkerParameters): Worker(context, params) {

    override fun doWork(): Result {

        var data = inputData.getString("data")
        return try {

            for (i in 0 until 6) {
                setProgressAsync(Data.Builder().putInt("progress", i * 100 / 5).build())
                Thread.sleep(600)
            }

            data = "$data, I'm orange"

            val outputData = Data.Builder()
            outputData.putString("data", data)
            Result.success(outputData.build())
        }
        catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}

class ProcessTestViewTealWorker(context: Context, params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result {

        var data = inputData.getString("data")
        return try {

            for (i in 0 until 6) {
                setProgressAsync(Data.Builder().putInt("progress", i * 100 / 5).build())
                Thread.sleep(1000)
            }

            data = "$data, I'm teal"

            val outputData = Data.Builder()
            outputData.putString("data", data)
            Result.success(outputData.build())
        }
        catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}

private fun createInputData(): Data {
    val builder = Data.Builder()
    builder.putString("data", "Hello")
    return builder.build()
}
