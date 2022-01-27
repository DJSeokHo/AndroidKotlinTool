package com.swein.androidkotlintool.main.examples.inpersonsigning

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.drawToBitmap
import com.swein.androidkotlintool.R

class InPersonSigningActivity : AppCompatActivity() {

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    private val signatureView: SignatureView by lazy {
        findViewById(R.id.signatureView)
    }

    private val buttonCreate: Button by lazy {
        findViewById(R.id.buttonCreate)
    }

    private val buttonClear: Button by lazy {
        findViewById(R.id.buttonClear)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_person_signing)

//        signatureView.setStrokeWidth(12f)
//        signatureView.setStrokeColor(Color.RED)
//        signatureView.setBackground(Color.BLACK)

        buttonCreate.setOnClickListener {
            val bitmap = signatureView.drawToBitmap(Bitmap.Config.ARGB_8888)
            imageView.setImageBitmap(bitmap)
        }

        buttonClear.setOnClickListener {
            signatureView.clear()
            imageView.setImageBitmap(null)
        }
    }
}