package com.swein.androidkotlintool.main.examples.email

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.systemphotopicker.SystemPhotoPickManager

class SendEmailExampleActivity : AppCompatActivity() {

    private val systemPhotoPickManager = SystemPhotoPickManager(this)

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    private val buttonPhoto: Button by lazy {
        findViewById(R.id.buttonPhoto)
    }

    private val buttonEmail: Button by lazy {
        findViewById(R.id.buttonEmail)
    }

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_email_example)

        buttonPhoto.setOnClickListener {

            systemPhotoPickManager.requestPermission {

                it.takePictureWithUri { uri ->

                    this.imageUri = uri

                    imageView.setImageURI(imageUri)

                }
            }
        }

        buttonEmail.setOnClickListener {

            imageUri?.let {
                mailToWithFile(this, it, listOf("test@gmail.com"), "Coding with cat", "Why not subscribe to my channel??")
            } ?: run {
                mailToWithText(this, listOf("test@gmail.com"), "Coding with cat", "Why not subscribe to my channel??")
            }

        }
    }


    private fun mailToWithText(context: Context, emailReceiver: List<String>, title: String, message: String) {
        val email = Intent(Intent.ACTION_SEND)
        email.type = "plain/text"
        email.putExtra(Intent.EXTRA_EMAIL, emailReceiver.toTypedArray())
        email.putExtra(Intent.EXTRA_SUBJECT, title)
        email.putExtra(Intent.EXTRA_TEXT, message)
        context.startActivity(Intent.createChooser(email, "select email app please"))
    }

    private fun mailToWithFile(
        context: Context,
        uri: Uri,
        emailReceiver: List<String>,
        title: String,
        message: String
    ) {

        val intent = Intent(Intent.ACTION_SEND)

        intent.type = "application/octet-stream"

        intent.putExtra(Intent.EXTRA_EMAIL, emailReceiver.toTypedArray())
        intent.putExtra(Intent.EXTRA_SUBJECT, title)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        context.startActivity(Intent.createChooser(intent, "select email app please"))
    }
}