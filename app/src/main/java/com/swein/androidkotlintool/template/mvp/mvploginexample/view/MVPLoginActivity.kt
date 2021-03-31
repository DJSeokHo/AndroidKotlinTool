package com.swein.androidkotlintool.template.mvp.mvploginexample.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.template.mvp.mvploginexample.presenter.ILoginPresenter
import com.swein.androidkotlintool.template.mvp.mvploginexample.presenter.LoginPresenter

/**
 *
 * Model <------ Presenter <==============> View
 *
 *
 */
class MVPLoginActivity : FragmentActivity(), ILoginView {

    private lateinit var editTextID: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonClear: Button
    private lateinit var frameLayoutProgress: FrameLayout
    private lateinit var textViewNickname: TextView
    private lateinit var textViewAge: TextView

    lateinit var iLoginPresenter: ILoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m_v_p_login)

        initPresenter()

        findView()
        setListener()
    }

    private fun initPresenter() {
        iLoginPresenter = LoginPresenter(this)
    }

    private fun findView() {
        editTextID = findViewById(R.id.editTextID)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonClear = findViewById(R.id.buttonClear)
        textViewNickname = findViewById(R.id.textViewNickname)
        textViewAge = findViewById(R.id.textViewAge)
        frameLayoutProgress = findViewById(R.id.frameLayoutProgress)
    }

    private fun setListener() {
        buttonLogin.setOnClickListener {
            iLoginPresenter.showProgress()
            buttonLogin.isClickable = false
            buttonClear.isClickable = false

            iLoginPresenter.login(editTextID.text.toString().trim(), editTextPassword.text.toString().trim())
        }

        buttonClear.setOnClickListener {
            iLoginPresenter.clear()
        }
    }

    override fun onClear() {
        editTextID.setText("")
        editTextPassword.setText("")
    }

    @SuppressLint("SetTextI18n")
    override fun onLoginSuccessUpdateNicknameUI(nickname: String) {
        ILog.debug("???", "view will update this UI about nickname field $nickname")
        textViewNickname.text = "nick name is '$nickname'"
    }

    @SuppressLint("SetTextI18n")
    override fun onLoginSuccessUpdateAgeUI(age: Int) {
        ILog.debug("???", "view will update this UI about age field $age")
        textViewAge.text = "age is '$age'"
    }

    override fun onShowProgress() {
        frameLayoutProgress.visibility = View.VISIBLE
    }

    override fun onHideProgress() {
        frameLayoutProgress.visibility = View.GONE
    }

}