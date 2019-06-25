package com.swein.androidkotlintool.framework.util.packages

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

class PackageUtil {

    /**
     * check APP and start APP
     */
    fun startApplicationWithPackageName(context: Context, packageNameString: String, withFinish: Boolean) {

        // get Activities, services, versioncode, name... from app package name
        var packageInfo: PackageInfo? = null

        try {
            packageInfo = context.packageManager.getPackageInfo(packageNameString, 0)
        }
        catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        if (packageInfo == null) {

            //should download or install app

            return
        }

        // create a Intent of package that type is CATEGORY_LAUNCHER
        val resolveIntent = Intent(Intent.ACTION_MAIN, null)
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        resolveIntent.setPackage(packageInfo.packageName)

        // getPackageManager() queryIntentActivities
        val resolveInfoList = context.packageManager
            .queryIntentActivities(resolveIntent, 0)

        val resolveInfo = resolveInfoList.iterator().next()

        resolveInfo?.let {
            val packageName = it.activityInfo.packageName
            val className = it.activityInfo.name
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)

            val componentName = ComponentName(packageName, className)

            intent.component = componentName
            context.startActivity(intent)

            if(withFinish) {
                System.exit(0)
            }
        }
    }
}