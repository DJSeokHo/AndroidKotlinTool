package com.swein.androidkotlintool.framework.module.firebase.demo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.model.MemberModel
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.viewmodel.MemberViewModel
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.demo.viewmodel.MemberViewModelState
import com.swein.androidkotlintool.framework.util.eventsplitshot.eventcenter.EventCenter
import com.swein.androidkotlintool.framework.util.eventsplitshot.subject.ESSArrows
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.jetpackexample.datastore.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginExampleActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {
            Intent(context, LoginExampleActivity::class.java).apply {
                context.startActivity(this)
            }
        }

    }

    private val buttonLogin: Button by lazy {
        findViewById(R.id.buttonLogin)
    }
    private val buttonRegister: Button by lazy {
        findViewById(R.id.buttonRegister)
    }

    private val progress: FrameLayout by lazy {
        findViewById(R.id.progress)
    }

    private val editTextID: EditText by lazy {
        findViewById(R.id.editTextID)
    }

    private val editTextPW: EditText by lazy {
        findViewById(R.id.editTextPW)
    }

    private val checkBox: CheckBox by lazy {
        findViewById(R.id.checkBox)
    }

    private val viewModel: MemberViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_example)

        initFlow()
        setListener()
    }

    private fun initFlow() {
        lifecycleScope.launch(Dispatchers.Main) {

            whenCreated {

                viewModel.memberViewModelState.collect {

                    when (it) {

                        is MemberViewModelState.CheckIDExistsSuccessfully -> {
                            hideProgress()

                            ILog.debug("???", it.id)

                            if (it.id == "") {
                                // fire store doesn't exist the id, can register

                                val id = editTextID.text.toString().trim()
                                val pw = editTextPW.text.toString().trim()

                                InputInfoExampleActivity.register(this@LoginExampleActivity, id, pw, checkBox.isChecked)
                            }
                            else {
                                Toast.makeText(this@LoginExampleActivity, "id has existed", Toast.LENGTH_SHORT).show()
                            }
                        }

                        is MemberViewModelState.RegisterSuccessfully -> {
                            hideProgress()

                            if (checkBox.isChecked) {

                                DataStoreManager.saveValue(this@LoginExampleActivity, MemberModel.UUID_KEY, it.memberModel.uuId)

                                val mutableMap = mutableMapOf<String, Any>()
                                mutableMap["memberModel"] = it.memberModel
                                EventCenter.sendEvent(ESSArrows.ON_UPDATE_PROFILE, this, mutableMap)
                                finish()
                            }
                        }

                        is MemberViewModelState.SignInSuccessfully -> {
                            hideProgress()
                        }

                        is MemberViewModelState.Error -> {
                            hideProgress()
                        }

                        is MemberViewModelState.Empty -> {
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
            login()
        }

        buttonRegister.setOnClickListener {
            register()
        }
    }

    private fun checkInput(onSuccess: (id: String, pw: String) -> Unit, onError: () -> Unit) {

        val id = editTextID.text.toString().trim()
        val pw = editTextPW.text.toString().trim()

        if (id == "" || pw == "") {
            onError()
        }
        else {
            onSuccess(id, pw)
        }
    }

    private fun login() {

        checkInput(onSuccess = { id, pw ->

            viewModel.signIn(id, pw)

        }, onError = {
            Toast.makeText(this, "input id and password please", Toast.LENGTH_SHORT).show()
        })

    }

    private fun register() {

        checkInput(onSuccess = { id, _ ->

            viewModel.isIdExists(id)

        }, onError = {
            Toast.makeText(this, "input id and password please", Toast.LENGTH_SHORT).show()
        })

    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }

}