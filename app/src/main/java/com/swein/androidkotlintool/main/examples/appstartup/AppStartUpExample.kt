package com.swein.androidkotlintool.main.examples.appstartup

import android.content.Context
import androidx.startup.Initializer
import com.swein.androidkotlintool.framework.util.log.ILog

/**
<provider
android:name="androidx.startup.InitializationProvider"
android:authorities="${applicationId}.androidx-startup"
android:exported="false"
tools:node="merge">
<!-- This entry makes ExampleLoggerInitializer discoverable. -->
<meta-data  android:name="com.example.ExampleLoggerInitializer"
android:value="androidx.startup" />
</provider>
 */
class AppStartUpExample: Initializer<Unit> {

    companion object {
        private const val TAG = "AppStartUpExample"
    }
    override fun create(context: Context) {
        ILog.debug(TAG, "create")
        AnyToolNeedContextWhenInit.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        ILog.debug(TAG, "dependencies")
        // No dependencies on other libraries.
        return emptyList()
    }
}