package com.swein.androidkotlintool.framework.module.firebase.demo

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.model.MemberModel
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.viewmodel.MemberViewModel
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.viewmodel.MemberViewModelState
import com.swein.androidkotlintool.framework.utility.date.DateUtility
import com.swein.androidkotlintool.framework.utility.eventsplitshot.eventcenter.EventCenter
import com.swein.androidkotlintool.framework.utility.eventsplitshot.subject.ESSArrows
import com.swein.androidkotlintool.framework.utility.glide.SHGlide
import com.swein.androidkotlintool.framework.utility.uuid.UUIDUtil
import com.swein.androidkotlintool.main.examples.systemphotopicker.SystemPhotoPickManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FirebaseDemoActivity : AppCompatActivity() {

    private val systemPhotoPickManager = SystemPhotoPickManager(this)

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    private val editTextID: EditText by lazy {
        findViewById(R.id.editTextID)
    }

    private val editTextPW: EditText by lazy {
        findViewById(R.id.editTextPW)
    }

    private val editTextNickname: EditText by lazy {
        findViewById(R.id.editTextNickname)
    }

    private val editTextEmail: EditText by lazy {
        findViewById(R.id.editTextEmail)
    }

    private val buttonRegister: Button by lazy {
        findViewById(R.id.buttonRegister)
    }

    private val buttonLogin: Button by lazy {
        findViewById(R.id.buttonLogin)
    }

    private val buttonModify: Button by lazy {
        findViewById(R.id.buttonModify)
    }

    private val buttonDelete: Button by lazy {
        findViewById(R.id.buttonDelete)
    }

    private val buttonClean: Button by lazy {
        findViewById(R.id.buttonClean)
    }

    private val linearLayoutPhotoSelector: LinearLayout by lazy {
        findViewById(R.id.linearLayoutPhotoSelector)
    }

    private val buttonCamera: Button by lazy {
        findViewById(R.id.buttonCamera)
    }

    private val buttonGallery: Button by lazy {
        findViewById(R.id.buttonGallery)
    }


    private val progress: FrameLayout by lazy {
        findViewById(R.id.progress)
    }

    private val viewModel: MemberViewModel by viewModels()

    private var tempImageUri: Uri? = null
    private var memberModel: MemberModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_demo)

        initObserver()
        initFlow()
        setListener()

    }

    private fun initObserver() {

        EventCenter.addEventObserver(ESSArrows.REQUEST_REFRESH, this, object : EventCenter.EventRunnable {
            override fun run(arrow: String, poster: Any, data: MutableMap<String, Any>?) {
                updateView()
            }
        })
    }

    private fun initFlow() {
        lifecycleScope.launch(Dispatchers.Main) {

            whenCreated {

                viewModel.memberViewModelState.collect {

                    when (it) {

                        is MemberViewModelState.RegisterSuccessfully -> {
                            hideProgress()
                            memberModel = it.memberModel
                            updateView()
                        }

                        is MemberViewModelState.SignInSuccessfully -> {
                            hideProgress()
                            memberModel = it.memberModel
                            updateView()
                        }

                        is MemberViewModelState.UpdateSuccessfully -> {
                            hideProgress()
                            memberModel = it.memberModel
                            updateView()
                        }

                        is MemberViewModelState.DeleteSuccessfully -> {
                            hideProgress()
                            clean()
                        }

                        is MemberViewModelState.Error -> {
                            hideProgress()
                            Toast.makeText(this@FirebaseDemoActivity, it.message, Toast.LENGTH_SHORT).show()
                        }

                        is MemberViewModelState.Loading -> {
                            showProgress()
                        }

                        else -> Unit
                    }
                }

            }

        }
    }

    private fun setListener() {

        buttonRegister.setOnClickListener {

            memberModel = MemberModel().apply {
                uuId = UUIDUtil.getUUIDString()
                id = editTextID.text.toString().trim()
                password = editTextPW.text.toString().trim()
                email = editTextEmail.text.toString().trim()
                nickname = editTextNickname.text.toString().trim()
                profileImageUrl = ""
                profileImageFileCloudPath = ""
                createDate = DateUtility.getCurrentDateTimeString()
                modifyDate = createDate
                createBy = uuId
                modifyBy = uuId
            }

            viewModel.register(tempImageUri!!, "${memberModel!!.uuId}.jpg", memberModel!!)
        }

        buttonLogin.setOnClickListener {

            val id = editTextID.text.toString().trim()
            val pw = editTextPW.text.toString().trim()

            viewModel.signIn(id, pw)
        }

        buttonModify.setOnClickListener {

            memberModel!!.nickname = editTextNickname.text.toString().trim()
            memberModel!!.email = editTextEmail.text.toString().trim()
            memberModel!!.modifyDate = DateUtility.getCurrentDateTimeString()
            memberModel!!.modifyBy = memberModel!!.uuId

            viewModel.modify(tempImageUri, memberModel!!)
        }

        buttonDelete.setOnClickListener {

            viewModel.delete(memberModel!!)
        }

        buttonClean.setOnClickListener {

            clean()
        }

        linearLayoutPhotoSelector.setOnClickListener {
            linearLayoutPhotoSelector.visibility = View.GONE
        }

        buttonCamera.setOnClickListener {
            systemPhotoPickManager.requestPermission {

                it.takePictureWithUri { uri ->

                    linearLayoutPhotoSelector.visibility = View.GONE
                    tempImageUri = uri
                    imageView.setImageURI(uri)
                }
            }
        }

        buttonGallery.setOnClickListener {
            systemPhotoPickManager.requestPermission {

                it.selectPicture { uri ->

                    linearLayoutPhotoSelector.visibility = View.GONE
                    tempImageUri = uri
                    imageView.setImageURI(uri)

                }
            }
        }

        imageView.setOnClickListener {

            linearLayoutPhotoSelector.visibility = View.VISIBLE
        }

    }

    private fun updateView() {

        SHGlide.setImage(imageView, url = memberModel!!.profileImageUrl)
        editTextNickname.setText(memberModel!!.nickname)
        editTextEmail.setText(memberModel!!.email)
    }

    private fun clean() {
        imageView.setImageBitmap(null)
        editTextID.setText("")
        editTextPW.setText("")
        editTextNickname.setText("")
        editTextEmail.setText("")
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        EventCenter.removeAllObserver(this)
    }

}