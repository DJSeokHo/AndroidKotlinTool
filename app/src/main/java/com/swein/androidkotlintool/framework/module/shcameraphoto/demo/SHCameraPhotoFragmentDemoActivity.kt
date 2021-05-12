package com.swein.androidkotlintool.framework.module.shcameraphoto.demo

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.shcameraphoto.camera.SHCameraPhotoFragment
import com.swein.androidkotlintool.framework.util.activity.ActivityUtil

class SHCameraPhotoFragmentDemoActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_s_h_camera_photo_fragment_demo)

        if (savedInstanceState == null) {

            ActivityUtil.addFragmentWithTAG(
                this,
                R.id.frameLayoutContainer,
                SHCameraPhotoFragment.newInstance(10),
                SHCameraPhotoFragment.TAG,
                false,
                null
            )

        }

//        AlbumSelectorWrapper.scanImageFile(
//            this,
//            object : AlbumSelectorWrapper.AlbumSelectorWrapperDelegate {
//                override fun onSuccess(
//                    albumFolderItemBeanList: MutableList<AlbumFolderItemBean>,
//                    albumSelectorItemBeanList: MutableList<AlbumSelectorItemBean>
//                ) {
//                    ILog.debug("????", "${albumSelectorItemBeanList?.size}")
//                }
//
//                override fun onError() {
//                    ILog.debug("????", "onError")
//                }
//
//            })
    }


}