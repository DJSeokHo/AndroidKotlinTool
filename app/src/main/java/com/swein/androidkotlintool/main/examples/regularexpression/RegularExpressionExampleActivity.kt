package com.swein.androidkotlintool.main.examples.regularexpression

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.framework.utility.regularexpression.RegularExpressionUtil
import java.util.regex.Pattern

class RegularExpressionExampleActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "RegularExpressionExampleActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regular_expression_example)

        /*
            ^: match the start position of string
            $: match the end position of string
         */
        val face = "\uD83D\uDE04"
        val helloString = "hello, coding with cat"
        // insert string to start
        ILog.debug(TAG, Pattern.compile("^").matcher(helloString).replaceAll("$face "))

        // insert string to end
        ILog.debug(TAG, Pattern.compile("$").matcher(helloString).replaceAll(" $face"))

        ILog.debug(TAG, Pattern.compile("\b").matcher(helloString).replaceAll(face))
    }
}