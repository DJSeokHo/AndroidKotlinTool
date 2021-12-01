package com.swein.androidkotlintool.framework.utility.intent

import android.app.Activity
import android.content.Intent

class IntentUtil {

    companion object {

        fun intentStartActionBackToHome(activity: Activity) {

            val intent = Intent()
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_HOME)
            activity.startActivity(intent)

        }

    }

}