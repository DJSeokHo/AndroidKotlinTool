package com.swein.androidkotlintool.framework.utility.mail

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.File

class MailUtil {

    companion object {

        fun mailTo(context: Context, email: String, title: String, message: String) {

            val uri = Uri.parse("mailto:$email")
            val emailInfo = arrayOf(email)
            val intent = Intent(Intent.ACTION_SENDTO, uri)

            intent.putExtra(Intent.EXTRA_CC, emailInfo)
            intent.putExtra(Intent.EXTRA_SUBJECT, title)

            intent.putExtra(Intent.EXTRA_TEXT, message)

            try {
                context.startActivity(Intent.createChooser(intent, "이메일 앱 선택하세요"))
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun mailToWithText(context: Context, emailReceiver: Array<String>, title: String, message: String) {
            val email = Intent(Intent.ACTION_SEND)
            email.type = "plain/text"
            email.putExtra(android.content.Intent.EXTRA_EMAIL, emailReceiver)
            email.putExtra(android.content.Intent.EXTRA_SUBJECT, title)
            email.putExtra(android.content.Intent.EXTRA_TEXT, message)
            context.startActivity(Intent.createChooser(email, "请选择邮件发送软件"))
        }

        fun mailToWithFile(
            context: Context,
            file: File?,
            emailReceiver: Array<String>,
            title: String,
            message: String
        ) {

            if (file == null) {
                return
            }

            val intent = Intent(Intent.ACTION_SEND)

            intent.type = "application/octet-stream"

            intent.putExtra(android.content.Intent.EXTRA_EMAIL, emailReceiver)
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, title)
            intent.putExtra(android.content.Intent.EXTRA_TEXT, message)
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            context.startActivity(Intent.createChooser(intent, "select email app please"))
        }
    }
}