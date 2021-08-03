package com.swein.androidkotlintool.framework.module.shcameraphoto.demo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.shcameraphoto.camera.SHCameraPhotoFragment
import com.swein.androidkotlintool.main.examples.permissionexample.PermissionManager

class SHCameraPhotoFragmentDemoActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_s_h_camera_photo_fragment_demo)

        if (savedInstanceState == null) {

            PermissionManager.requestPermission(this,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE) {

                startCamera()
            }
        }

    }

    private fun startCamera() {

        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.frameLayoutContainer, SHCameraPhotoFragment.newInstance(10), SHCameraPhotoFragment.TAG)
            .commitAllowingStateLoss()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        PermissionManager.onActivityResult(requestCode, resultCode)
    }
}