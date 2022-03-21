package com.swein.androidkotlintool.framework.utility.mail

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.File

object MailUtility {

    fun mailTo(context: Context, email: String, title: String, message: String, chooserTitle: String) {

        val uri = Uri.parse("mailto:$email")
        val emailInfo = arrayOf(email)
        val intent = Intent(Intent.ACTION_SENDTO, uri)

        intent.putExtra(Intent.EXTRA_CC, emailInfo)
        intent.putExtra(Intent.EXTRA_SUBJECT, title)

        intent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            context.startActivity(Intent.createChooser(intent, chooserTitle))
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun mailToWithText(context: Context, emailReceiver: List<String>, title: String, message: String, chooserTitle: String) {
        val email = Intent(Intent.ACTION_SEND)
        email.type = "plain/text"
        email.putExtra(Intent.EXTRA_EMAIL, emailReceiver.toTypedArray())
        email.putExtra(Intent.EXTRA_SUBJECT, title)
        email.putExtra(Intent.EXTRA_TEXT, message)
        context.startActivity(Intent.createChooser(email, chooserTitle))
    }

    fun mailToWithFile(
        context: Context,
        file: File?,
        emailReceiver: List<String>,
        title: String,
        message: String,
        chooserTitle: String
    ) {

        if (file == null) {
            return
        }

        val intent = Intent(Intent.ACTION_SEND)

        intent.type = "application/octet-stream"

        intent.putExtra(Intent.EXTRA_EMAIL, emailReceiver.toTypedArray())
        intent.putExtra(Intent.EXTRA_SUBJECT, title)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
        context.startActivity(Intent.createChooser(intent, chooserTitle))
    }
}