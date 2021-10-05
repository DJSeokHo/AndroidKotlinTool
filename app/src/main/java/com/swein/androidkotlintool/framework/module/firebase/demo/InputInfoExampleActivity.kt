package com.swein.androidkotlintool.framework.module.firebase.demo

import android.content.Context
import android.content.Intent
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
import com.swein.androidkotlintool.framework.util.date.DateUtility
import com.swein.androidkotlintool.framework.util.eventsplitshot.eventcenter.EventCenter
import com.swein.androidkotlintool.framework.util.eventsplitshot.subject.ESSArrows
import com.swein.androidkotlintool.framework.util.glide.SHGlide
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.uuid.UUIDUtil
import com.swein.androidkotlintool.main.examples.systemphotopicker.SystemPhotoPickManager
import com.swein.androidkotlintool.main.jetpackexample.datastore.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class InputInfoExampleActivity : AppCompatActivity() {

    private val systemPhotoPickManager = SystemPhotoPickManager(this)

    companion object {

        fun modify(context: Context, memberModel: MemberModel? = null) {

            Intent(context, InputInfoExampleActivity::class.java).apply {
                putExtra("modelBundle", memberModel?.toBundle())
                context.startActivity(this)
            }
        }

        fun register(context: Context, id: String, pw: String, autoLogin: Boolean) {

            val bundle = Bundle()
            bundle.putString("id", id)
            bundle.putString("pw", pw)
            bundle.putBoolean("autoLogin", autoLogin)

            Intent(context, InputInfoExampleActivity::class.java).apply {
                putExtra("bundle", bundle)
                context.startActivity(this)
            }
        }
    }

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    private val editTextNickname: EditText by lazy {
        findViewById(R.id.editTextNickname)
    }
    private val editTextEmail: EditText by lazy {
        findViewById(R.id.editTextEmail)
    }

    private val button: Button by lazy {
        findViewById(R.id.button)
    }

    private val progress: FrameLayout by lazy {
        findViewById(R.id.progress)
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

    private var imageUri: Uri? = null

    private var memberModel: MemberModel? = null

    private var id = ""
    private var pw = ""
    private var autoLogin = false

    private val viewModel: MemberViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_info_example)

        checkBundle()

        initFlow()
        setListener()

        updateView()
    }

    private fun checkBundle() {

        if (intent.hasExtra("modelBundle")) {
            intent.getBundleExtra("modelBundle")?.let {
                memberModel = MemberModel()
                memberModel!!.parsing(it)
            }
        }
        else {
            intent.getBundleExtra("bundle")?.let {
                id = it.getString("id", "")
                pw = it.getString("pw", "")
                autoLogin = it.getBoolean("autoLogin", false)
                ILog.debug("???", "$id $pw $autoLogin")
            }
        }
    }

    private fun initFlow() {
        lifecycleScope.launch(Dispatchers.Main) {

            whenCreated {

                viewModel.memberViewModelState.collect {

                    when (it) {

                        is MemberViewModelState.RegisterSuccessfully -> {
                            hideProgress()

                            if (autoLogin) {
                                // if should auto login
                                // save the uuid
                                // when app start, use uuId to get member info
                                DataStoreManager.saveValue(this@InputInfoExampleActivity, MemberModel.UUID_KEY, it.memberModel.uuId)
                            }

                            val mutableMap = mutableMapOf<String, Any>()
                            mutableMap["memberModel"] = it.memberModel
                            EventCenter.sendEvent(ESSArrows.ON_REGISTER, this, mutableMap)
                            finish()
                        }

                        is MemberViewModelState.UpdateSuccessfully -> {
                            hideProgress()

                            val mutableMap = mutableMapOf<String, Any>()
                            mutableMap["memberModel"] = it.memberModel
                            EventCenter.sendEvent(ESSArrows.ON_UPDATE_PROFILE, this, mutableMap)
                            finish()
                        }

                        is MemberViewModelState.Error -> {
                            hideProgress()
                            Toast.makeText(this@InputInfoExampleActivity, it.message, Toast.LENGTH_SHORT).show()
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

    private fun updateView() {
        memberModel?.let {
            editTextNickname.setText(it.nickname)
            editTextEmail.setText(it.email)

            SHGlide.setImage(imageView, url = it.profileImageUrl)

            button.text = "Modify"

            return
        }

        button.text = "Register"
    }

    private fun setListener() {

        linearLayoutPhotoSelector.setOnClickListener {
            linearLayoutPhotoSelector.visibility = View.GONE
        }

        buttonCamera.setOnClickListener {
            systemPhotoPickManager.requestPermission {

                it.takePictureWithUri { uri ->
                    imageUri = uri
                }
            }
        }

        buttonGallery.setOnClickListener {
            systemPhotoPickManager.requestPermission {

                it.selectPicture { uri ->

                    imageUri = uri

//                    lifecycleScope.launch(Dispatchers.Main) {
//
//                        val result = async {
//                            FirebaseStorageManager.uploadImage(
//                                uri = uri,
//                                folderName = FirebaseStorageManager.MEMBER_IMAGE_FOLDER,
//                                fileName = "test.jpg"
//                            )
//                        }
//
//                        val imageUrl = result.await()
//                        ILog.debug("???", imageUrl)
//                        SHGlide.setImage(imageView, url = imageUrl)
//                    }

                }
            }
        }

        imageView.setOnClickListener {

            linearLayoutPhotoSelector.visibility = View.VISIBLE
        }

        button.setOnClickListener {

            memberModel?.let {

                // register
                register()
                return@setOnClickListener
            }

            // modify
            modify()
        }
    }

    private fun register() {

        val nickname = editTextNickname.text.toString().trim()
        val email = editTextEmail.text.toString().trim()

        if (imageUri == null) {
            Toast.makeText(this, "photo is empty", Toast.LENGTH_SHORT).show()
            return
        }

        if (nickname == "" || email == "") {
            Toast.makeText(this, "input nickname and email please", Toast.LENGTH_SHORT).show()
            return
        }

        val memberModel = MemberModel()

        val uuId = UUIDUtil.getUUIDString()

        memberModel.uuId = uuId
        memberModel.id = id
        memberModel.password = pw
        memberModel.email = email
        memberModel.nickname = nickname
        memberModel.profileImageUrl = ""
        memberModel.profileImageFileCloudPath = ""
        memberModel.createDate = DateUtility.getCurrentDateTimeString()
        memberModel.modifyDate = memberModel.createDate
        memberModel.createBy = uuId
        memberModel.modifyBy = uuId

        viewModel.register(imageUri!!, "$uuId.jpg", memberModel)
    }

    private fun modify() {

        val nickname = editTextNickname.text.toString().trim()
        val email = editTextEmail.text.toString().trim()

        if (nickname == "" || email == "") {
            Toast.makeText(this, "input nickname and email please", Toast.LENGTH_SHORT).show()
            return
        }

        memberModel!!.nickname = nickname
        memberModel!!.email = email
        memberModel!!.modifyDate = DateUtility.getCurrentDateTimeString()

        viewModel.modify(imageUri, memberModel!!)
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }
}