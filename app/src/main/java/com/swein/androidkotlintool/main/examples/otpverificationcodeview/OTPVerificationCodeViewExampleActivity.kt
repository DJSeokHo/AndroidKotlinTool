package com.swein.androidkotlintool.main.examples.otpverificationcodeview

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.swein.androidkotlintool.R
import java.util.concurrent.TimeUnit


class OTPVerificationCodeViewExampleActivity : AppCompatActivity() {

    private val frameLayoutRoot: FrameLayout by lazy {
        findViewById(R.id.frameLayoutRoot)
    }

    private val editTextOne: EditText by lazy {
        findViewById(R.id.editTextOne)
    }
    private val editTextTwo: EditText by lazy {
        findViewById(R.id.editTextTwo)
    }
    private val editTextThree: EditText by lazy {
        findViewById(R.id.editTextThree)
    }
    private val editTextFour: EditText by lazy {
        findViewById(R.id.editTextFour)
    }
    private val editTextFive: EditText by lazy {
        findViewById(R.id.editTextFive)
    }
    private val editTextSix: EditText by lazy {
        findViewById(R.id.editTextSix)
    }

    private val textViewCountDown: TextView by lazy {
        findViewById(R.id.textViewCountDown)
    }

    private val buttonVerify: Button by lazy {
        findViewById(R.id.buttonVerify)
    }

    private val progress: FrameLayout by lazy {
        findViewById(R.id.progress)
    }

    private var counterDownSecond = 10L
    private var timer: CountDownTimer = object : CountDownTimer((counterDownSecond * 1000), 1000) {

        override fun onTick(millisUntilFinished: Long) {

            textViewCountDown.text = "count down seconds: ${(millisUntilFinished * 0.001).toInt()}"
        }

        override fun onFinish() {
            Toast.makeText(this@OTPVerificationCodeViewExampleActivity, "Time out, Verify again", Toast.LENGTH_LONG).show()
            textViewCountDown.text = ""
            dismissSoftKeyboard()
            stopCountDown()
            reset()
        }

    }


    private var storedVerificationId = ""

    private val options = PhoneAuthOptions.newBuilder(Firebase.auth)
        .setPhoneNumber("+1 1112221234")       // Phone number to verify
        .setTimeout(counterDownSecond, TimeUnit.SECONDS) // Timeout and unit
        .setActivity(this)                 // Activity (for callback binding)
        .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
            }

            override fun onVerificationFailed(firebaseException: FirebaseException) {
                Toast.makeText(this@OTPVerificationCodeViewExampleActivity, "Phone number format is incorrect", Toast.LENGTH_LONG).show()
                hideProgress()
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                hideProgress()
                initFocus()
                startCountDown()
                this@OTPVerificationCodeViewExampleActivity.storedVerificationId = verificationId
            }

            override fun onCodeAutoRetrievalTimeOut(verificationId: String) {
                super.onCodeAutoRetrievalTimeOut(verificationId)
                // I don't do anything in here, because I'll process time out in timer
            }
        })
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverification_code_view_example)

        setListener()

        initFirebase()
//        initFocus()
//        startCountDown()
    }

    private fun setListener() {

        frameLayoutRoot.setOnClickListener {

            dismissSoftKeyboard()
        }

        buttonVerify.setOnClickListener {
            PhoneAuthProvider.verifyPhoneNumber(options)
            showProgress()
        }

        setTextChangeListener(editTextOne, editTextTwo)
        setTextChangeListener(editTextTwo, editTextThree)
        setTextChangeListener(editTextThree, editTextFour)
        setTextChangeListener(editTextFour, editTextFive)
        setTextChangeListener(editTextFive, editTextSix)
        setTextChangeListener(editTextSix, done = {
            verifyOTPCode()
        })

        setKeyListener(editTextTwo, editTextOne)
        setKeyListener(editTextThree, editTextTwo)
        setKeyListener(editTextFour, editTextThree)
        setKeyListener(editTextFive, editTextFour)
        setKeyListener(editTextSix, editTextFive)
    }

    private fun dismissSoftKeyboard() {
        val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(frameLayoutRoot.windowToken, 0)
    }

    private fun setTextChangeListener(fromEditText: EditText, targetEditText: EditText? = null, done: (() -> Unit)? = null) {

        fromEditText.addTextChangedListener {

            it?.let { string ->
                if (string.isNotEmpty()) {

                    targetEditText?.let { editText ->
                        editText.isEnabled = true
                        editText.requestFocus()
                    } ?: run {
                        done?.let { done ->
                            done()
                        }
                    }

                    fromEditText.clearFocus()
                    fromEditText.isEnabled = false
                }
            }
        }
    }

    private fun setKeyListener(fromEditText: EditText, backToEditText: EditText) {
        fromEditText.setOnKeyListener { _, _, event ->

            if (null != event && KeyEvent.KEYCODE_DEL == event.keyCode) {
                backToEditText.isEnabled = true
                backToEditText.requestFocus()
                backToEditText.setText("")

                fromEditText.clearFocus()
                fromEditText.isEnabled = false
            }
            false
        }
    }

    private fun initFocus() {

        editTextOne.isEnabled = true

        editTextOne.postDelayed({
            editTextOne.isEnabled = true
            editTextOne.requestFocus()
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(editTextOne, InputMethodManager.SHOW_FORCED)
        }, 500)

    }

    private fun reset() {

        editTextOne.isEnabled = false
        editTextTwo.isEnabled = false
        editTextThree.isEnabled = false
        editTextFour.isEnabled = false
        editTextFive.isEnabled = false
        editTextSix.isEnabled = false

        editTextOne.setText("")
        editTextTwo.setText("")
        editTextThree.setText("")
        editTextFour.setText("")
        editTextFive.setText("")
        editTextSix.setText("")


    }

    private fun verifyOTPCode() {
        val otpCode = "${editTextOne.text.toString().trim()}${editTextTwo.text.toString().trim()}" +
                "${editTextThree.text.toString().trim()}${editTextFour.text.toString().trim()}" +
                "${editTextFive.text.toString().trim()}${editTextSix.text.toString().trim()}"

        if (6 != otpCode.length) {
            return
        }

        showProgress()
        val credential = PhoneAuthProvider.getCredential(storedVerificationId, otpCode)
        signInWithPhoneAuthCredential(credential)
    }

    private fun initFirebase() {
//        Firebase.auth.languageCode = "kr"
        Firebase.auth.useAppLanguage()
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Verify success", Toast.LENGTH_LONG).show()
                    dismissSoftKeyboard()
                    stopCountDown()
                    textViewCountDown.text = ""
                }
                else {
                    Toast.makeText(this, "Verify failed, input again", Toast.LENGTH_LONG).show()
                    textViewCountDown.text = ""
                    reset()
                    initFocus()
                }

                hideProgress()
            }
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }

    private fun startCountDown() {
        timer.start()
    }

    private fun stopCountDown() {
        timer.cancel()
    }

    override fun onDestroy() {
        stopCountDown()
        super.onDestroy()
    }
}