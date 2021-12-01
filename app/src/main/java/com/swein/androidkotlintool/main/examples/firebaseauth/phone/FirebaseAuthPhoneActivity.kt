package com.swein.androidkotlintool.main.examples.firebaseauth.phone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import java.util.concurrent.TimeUnit

class FirebaseAuthPhoneActivity : AppCompatActivity() {

    private var storedVerificationId: String? = null
//    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_firebase_auth_phone)

//        Firebase.auth.languageCode = "kr"
        Firebase.auth.useAppLanguage()

        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber("+82 01055060960")       // Phone number to verify
            .setTimeout(30L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    // This callback will be invoked in two situations:
                    // 1 - Instant verification. In some cases the phone number can be instantly
                    //     verified without needing to send or enter a verification code.
                    // 2 - Auto-retrieval. On some devices Google Play services can automatically
                    //     detect the incoming verification SMS and perform verification without
                    //     user action.
                    ILog.debug("???", "onVerificationCompleted smsCode:${phoneAuthCredential.smsCode}")
                    ILog.debug("???", "onVerificationCompleted signInMethod:${phoneAuthCredential.signInMethod}")
                    ILog.debug("???", "onVerificationCompleted provider:${phoneAuthCredential.provider}")

                }

                override fun onVerificationFailed(firebaseException: FirebaseException) {
                    // This callback is invoked in an invalid request for verification is made,
                    // for instance if the the phone number format is not valid.
                    ILog.debug("???", "onVerificationFailed: ${firebaseException.message}")

                    if (firebaseException is FirebaseAuthInvalidCredentialsException) {
                        // Invalid request
                    }
                    else if (firebaseException is FirebaseTooManyRequestsException) {
                        // The SMS quota for the project has been exceeded

                    }

                    // Show a message and update the UI
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    // The SMS verification code has been sent to the provided phone number, we
                    // now need to ask the user to enter the code and then construct a credential
                    // by combining the code with a verification ID.
                    ILog.debug("???", "onCodeSent:$verificationId")
//                    ILog.debug("???", "token:${token.toString()}")

                    // Save verification ID and resending token so we can use them later
                    this@FirebaseAuthPhoneActivity.storedVerificationId = verificationId
//                    this@FirebaseAuthPhoneActivity.resendToken = token
                }

                override fun onCodeAutoRetrievalTimeOut(verificationId: String) {
                    super.onCodeAutoRetrievalTimeOut(verificationId)

                    ILog.debug("???", "onCodeAutoRetrievalTimeOut:$verificationId")
                }
            })
            .build()


        findViewById<Button>(R.id.button).setOnClickListener {
            ILog.debug("???", "click??")
            PhoneAuthProvider.verifyPhoneNumber(options)
        }

        findViewById<Button>(R.id.buttonAuth).setOnClickListener {
            ILog.debug("???", "click?signInWithCredential?")

            val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)
            signInWithPhoneAuthCredential(credential)

            code = "123456"
        }
    }

    var code = "123457"
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    ILog.debug("???", "signInWithCredential:success")
                    ILog.debug("???", "${task.result.toString()}")

                    val user = task.result?.user
                    ILog.debug("???", "$user")
                } else {
                    // Sign in failed, display a message and update the UI
                    ILog.debug("???", "signInWithCredential:failure ${task.exception}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    override fun onDestroy() {
        Firebase.auth.signOut()
        super.onDestroy()
    }
}