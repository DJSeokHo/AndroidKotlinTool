package com.swein.androidkotlintool.framework.module.firebase.demo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.viewmodel.MemberViewModel
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.viewmodel.MemberViewModelState
import com.swein.androidkotlintool.framework.util.eventsplitshot.eventcenter.EventCenter
import com.swein.androidkotlintool.framework.util.eventsplitshot.subject.ESSArrows
import com.swein.androidkotlintool.framework.util.glide.SHGlide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// top-level define
private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "whatever_name")

class FirebaseDemoActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "FirebaseDemoActivity"
    }

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
                // sign up
                signUp()
            }
        }

        buttonModify.setOnClickListener {
            if (isAlreadySignIn) {
                // modify
                modify()
            }
            else {
                // sign in
                signIn()
            }
        }

    }

    private fun updateView() {

        if (isAlreadySignIn) {

            SHGlide.setImage(imageView, url = viewModel.memberModel!!.profileImageUrl)
            textViewNickname.text = viewModel.memberModel!!.nickname
            textViewEmail.text = viewModel.memberModel!!.email

        }
        else {

            imageView.setImageDrawable(null)
            textViewNickname.text = ""
            textViewEmail.text = ""

        }
    }


    private fun modify() {

        viewModel.memberModel?.let {
            val intent = Intent(this, ModifyExampleActivity::class.java)

            intent.putExtra("modifyBundle", it.toBundle())

            startActivity(intent)
        }

    }


    private fun logout() {

        viewModel.memberModel?.let {
            lifecycleScope.launch {

                viewModel.memberModel = null
                isAlreadySignIn = false

                updateView()
            }
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