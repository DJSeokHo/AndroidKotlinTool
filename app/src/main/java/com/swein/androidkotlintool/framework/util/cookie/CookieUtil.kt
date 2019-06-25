package com.swein.androidkotlintool.framework.util.cookie

import android.content.Context
import android.os.Build
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import com.swein.androidkotlintool.framework.util.log.ILog

class CookieUtil {

    companion object {

        /**
         * must add this in WebViewClient - onPageFinished
         *
         * public void onPageFinished(WebView view, String url) {
         * super.onPageFinished(view, url);
         *
         * // add here !!!
         * CookieUtil.syncCookies(context);
         *
         * @param context
         */
        fun syncCookies(context: Context) {

            // clear cookie
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {

                CookieManager.getInstance().flush()
            } else {
                CookieSyncManager.createInstance(context).sync()
            }

        }


        /**
         * put this in here:
         * @see {@link WebViewClient.onPageFinished
         * @param url from web view url
         * @param key the key of the value what you want
         *
         * @return the value what you want
         */
        fun getValueFromCookie(url: String, key: String): String {
            val cookieManager = CookieManager.getInstance()
            val cookie = cookieManager.getCookie(url)

            if (cookie == null || cookie.length == 0) {
                return ""
            }

            val value: String
            val items = cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (item in items) {
                if (item.trim { it <= ' ' }.contains(key)) {

                    val target =
                        item.trim { it <= ' ' }.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    if (target != null && target.size > 1) {
                        value = target[1]

                        return value
                    }
                }
            }

            return ""
        }

        fun getKeyValueFromCookie(url: String, key: String): String {
            val cookieManager = CookieManager.getInstance()
            val cookie = cookieManager.getCookie(url)
            if (cookie == null || cookie.length == 0) {
                return ""
            }

            val value: String
            val items = cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (item in items) {

                val target = item.trim { it <= ' ' }.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (target != null && target.size > 1) {

                    if (target[0].trim { it <= ' ' } == key) {

                        value = target[1]
                        return value
                    }
                }
            }

            return ""
        }

        /**
         * sync cookie
         * @param context context
         * @param url link
         * @param value value
         */
        fun syncCookie(context: Context, url: String, value: String) {

            CookieSyncManager.createInstance(context)
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.removeAllCookie()
            cookieManager.setCookie(url, value)

            val cookie = cookieManager.getCookie(url)
            ILog.debug("syncCookie", cookie)

            CookieSyncManager.getInstance().sync()
        }

        /**
         * clear cookie from web view
         * @param context
         */
        fun clearCookie(context: Context) {

            // clear cookie
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {

                CookieManager.getInstance().removeAllCookies(null)
                CookieManager.getInstance().flush()
            }
            else {

                val cookieSyncMngr = CookieSyncManager.createInstance(context)
                cookieSyncMngr.startSync()
                val cookieManager = CookieManager.getInstance()
                cookieManager.removeAllCookie()
                cookieManager.removeSessionCookie()
                cookieSyncMngr.stopSync()
                cookieSyncMngr.sync()
            }
        }

    }
}