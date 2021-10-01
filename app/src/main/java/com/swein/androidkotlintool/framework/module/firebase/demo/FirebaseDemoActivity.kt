package com.swein.androidkotlintool.framework.module.firebase.demo

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
import com.swein.androidkotlintool.framework.util.eventsplitshot.eventcenter.EventCenter
import com.swein.androidkotlintool.framework.util.eventsplitshot.subject.ESSArrows
import com.swein.androidkotlintool.framework.util.glide.SHGlide
import com.swein.androidkotlintool.main.jetpackexample.datastore.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FirebaseDemoActivity : AppCompatActivity() {

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    private val textViewNickname: TextView by lazy {
        findViewById(R.id.textViewNickname)
    }
    private val textViewEmail: TextView by lazy {
        findViewById(R.id.textViewEmail)
    }

    private val buttonLogin: Button by lazy {
        findViewById(R.id.buttonLogin)
    }
    private val buttonModify: Button by lazy {
        findViewById(R.id.buttonModify)
    }

    private val progress: FrameLayout by lazy {
        findViewById(R.id.progress)
    }

    private var isAlreadySignIn = false

    private val viewModel: MemberViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_demo)

        initObserver()
        initFlow()
        setListener()

    }

    private fun initObserver() {
        EventCenter.addEventObserver(ESSArrows.ON_UPDATE_PROFILE, this, object : EventCenter.EventRunnable {
            override fun run(arrow: String, poster: Any, data: MutableMap<String, Any>?) {

                data?.let {
                    isAlreadySignIn = true

                    val memberModel = data["memberModel"] as MemberModel

                    updateView(memberModel)
                }

            }
        })
    }

    private fun initFlow() {
        lifecycleScope.launch(Dispatchers.Main) {

            whenCreated {

                viewModel.memberViewModelState.collect {

                    when (it) {

                        is MemberViewModelState.AutoSignInSuccessfully -> {
                            hideProgress()
                        }

                        is MemberViewModelState.Error -> {
                            hideProgress()
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

        buttonLogin.setOnClickListener {

            if (isAlreadySignIn) {
                // logout
                logout()
            }
            else {
                LoginExampleActivity.start(this)
            }
        }

        buttonModify.setOnClickListener {
//            if (isAlreadySignIn) {
//                // modify
//                modify()
//            }
//            else {
//                // sign in
//                signIn()
//            }
        }

    }

    private fun updateView(memberModel: MemberModel? = null) {

        memberModel?.let {
            SHGlide.setImage(imageView, url = it.profileImageUrl)
            textViewNickname.text = it.nickname
            textViewEmail.text = it.email
        } ?: run {
            imageView.setImageDrawable(null)
            textViewNickname.text = ""
            textViewEmail.text = ""
        }
    }


//    private fun modify() {
//
//        viewModel.memberModel?.let {
//            val intent = Intent(this, InputInfoExampleActivity::class.java)
//
//            intent.putExtra("modifyBundle", it.toBundle())
//
//            startActivity(intent)
//        }
//
//    }


    private fun logout() {

        lifecycleScope.launch {

            DataStoreManager.saveValue(this@FirebaseDemoActivity, MemberModel.UUID_KEY, "")
            isAlreadySignIn = false

            updateView()
        }
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