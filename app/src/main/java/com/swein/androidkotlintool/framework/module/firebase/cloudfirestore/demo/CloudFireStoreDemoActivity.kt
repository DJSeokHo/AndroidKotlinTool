package com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.model.MemberModel
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.viewmodel.MemberViewModel
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.viewmodel.MemberViewModelState
import com.swein.androidkotlintool.framework.util.date.DateUtility
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.uuid.UUIDUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class CloudFireStoreDemoActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "CloudFireStoreDemoActivity"
    }

    private val progress: FrameLayout by lazy {
        findViewById(R.id.progress)
    }

    private val memberViewModel: MemberViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloud_fire_store_demo)

        initFlow()

        val memberModel = MemberModel()
        val uuid = UUIDUtil.getUUIDString()
        memberModel.uuId = uuid
        memberModel.id = "test"
        memberModel.password = "qwer1234"
        memberModel.email = "test@test.com"
        memberModel.nickname = "tester"
        memberModel.profileImageUrl = ""
        memberModel.profileImageFileCloudPath = ""
        memberModel.createDate = DateUtility.getCurrentDateTimeString()
        memberModel.modifyDate = DateUtility.getCurrentDateTimeString()
        memberModel.createBy = uuid
        memberModel.modifyBy = uuid

//        memberViewModel.register(memberModel)
        memberViewModel.signIn("test", "qwer1234")
    }

    private fun initFlow() {
        lifecycleScope.launch(Dispatchers.Main) {

            whenCreated {

                memberViewModel.memberViewModelState.collect {

                    when (it) {

                        is MemberViewModelState.RegisterSuccessfully -> {
                            ILog.debug(TAG, it.memberModel)
                            hideProgress()
                        }

                        is MemberViewModelState.SignSuccessfully -> {
                            ILog.debug(TAG, it.memberModel.to())
                            hideProgress()
                        }

                        is MemberViewModelState.Error -> {
                            Toast.makeText(this@CloudFireStoreDemoActivity, it.message, Toast.LENGTH_SHORT).show()
                            hideProgress()
                        }

                        is MemberViewModelState.Empty -> {
                            Toast.makeText(this@CloudFireStoreDemoActivity, "empty", Toast.LENGTH_SHORT).show()
                            hideProgress()
                        }

                        is MemberViewModelState.Loading -> {
                            showProgress()
                        }

                        is MemberViewModelState.None -> Unit
                    }
                }

            }

        }
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }
}