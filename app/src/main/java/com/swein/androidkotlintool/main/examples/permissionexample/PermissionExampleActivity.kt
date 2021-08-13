package com.swein.androidkotlintool.main.examples.permissionexample

import android.Manifest
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.toast.ToastUtility

class PermissionExampleActivity : AppCompatActivity() {

    private val permissionManager = PermissionManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_example)

        findViewById<Button>(R.id.button).setOnClickListener {

            permissionManager.requestPermission(
                "Permission",
                "permissions are necessary",
                "setting",
                arrayOf(Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            ) {

                yourMethodShouldRunAfterAllPermissionGranted()
            }
//            requestMultiplePermissions.launch(
//                arrayOf(
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_EXTERNAL_STORAGE
//                )
//            )

//            permissionManager.requestPermission(
//                "Permission",
//                "permissions are necessary",
//                "setting",
//                arrayOf(Manifest.permission.CAMERA,
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_EXTERNAL_STORAGE)
//                ) {
//
//                yourMethodShouldRunAfterAllPermissionGranted()
//            }
        }
    }

    private fun yourMethodShouldRunAfterAllPermissionGranted() {
        ToastUtility.showLongToastNormal(this, "All permission is ok, go go go")
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        PermissionManager.onActivityResult(requestCode, resultCode)
//        super.onActivityResult(requestCode, resultCode, data)
//    }
}