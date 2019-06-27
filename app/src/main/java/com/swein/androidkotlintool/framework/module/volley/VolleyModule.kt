package com.swein.androidkotlintool.framework.module.volley

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

interface VolleyModuleDelegate {
    fun onResponse(response: String)
    fun onErrorResponse(error: VolleyError)
}

class VolleyModule {

    companion object {
        private const val TAG = "VolleyModule"
    }

    private var queue: RequestQueue

    constructor(context: Context) {
        queue = Volley.newRequestQueue(context)
    }

    fun requestUrlGet(url: String, volleyModuleDelegate: VolleyModuleDelegate) {

        val stringRequest: StringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> {
                volleyModuleDelegate.onResponse(it)
            },
            Response.ErrorListener {
                volleyModuleDelegate.onErrorResponse(it)
            })

        queue.add(stringRequest)
    }

    fun requestUrlPost(url: String, volleyModuleDelegate: VolleyModuleDelegate, hashMap: HashMap<String, String>) {

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

        queue.add(stringRequest)
    }

}