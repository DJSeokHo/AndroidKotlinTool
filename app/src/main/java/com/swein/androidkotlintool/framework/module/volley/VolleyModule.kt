package com.swein.androidkotlintool.framework.module.volley

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

/**
 * After Android 9.0
 * volley can not access http
 *
 * so add this in AndroidManifest.xml between the <application></application>
 *
 * <uses-library android:name="org.apache.http.legacy" android:required="false"/>
 *
 * and add android:usesCleartextTraffic="true" in the <application>
 */
object VolleyModule {

    private const val TAG = "VolleyModule"

    interface VolleyModuleDelegate {
        fun onResponse(response: String)
        fun onErrorResponse(error: VolleyError)
    }

    private var queue: RequestQueue? = null

    fun getInstance(): VolleyModule {
        return this
    }

    fun requestUrlGet(context: Context, url: String, volleyModuleDelegate: VolleyModuleDelegate) {

        if(queue == null) {
            queue = Volley.newRequestQueue(context)
        }

        val stringRequest: StringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> {
                volleyModuleDelegate.onResponse(it)
            },
            Response.ErrorListener {
                volleyModuleDelegate.onErrorResponse(it)
            })

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun requestUrlPost(context: Context, url: String, volleyModuleDelegate: VolleyModuleDelegate, hashMap: HashMap<String, String>?) {

        if(queue == null) {
            queue = Volley.newRequestQueue(context)
        }

        val stringRequest = object : StringRequest(Request.Method.POST, url,
            Response.Listener<String> {
                volleyModuleDelegate.onResponse(it)
            },
            Response.ErrorListener {
                volleyModuleDelegate.onErrorResponse(it)
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                // if need, add this can get correct content response
                //                Map<String,String> params = new HashMap<>();
                //                params.put("Content-Type","application/json");
                //                params.put("Accept","application/json");
                //
                //                return params;

                return hashMap ?: java.util.HashMap()

            }
        }

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    /**
     * add this when MainActivity finish
     */
    fun close() {
        if (queue != null) {
            queue?.cancelAll(TAG)
        }
    }

}