package com.swein.androidkotlintool.main.examples.inpersonsigning

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.drawToBitmap
import com.swein.androidkotlintool.R

class InPersonSigningActivity : AppCompatActivity() {

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    private val frameLayoutContainer: FrameLayout by lazy {
        findViewById(R.id.frameLayoutContainer)
    }

    private val buttonCreate: Button by lazy {
        findViewById(R.id.buttonCreate)
    }

    private val buttonClear: Button by lazy {
        findViewById(R.id.buttonClear)
    }

    lateinit var signatureView: SignatureView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_person_signing)

        signatureView = SignatureView(this).apply {
            setStrokeWidth(12f)
            setStrokeColor(Color.RED)
            setBackground(Color.BLACK)

            frameLayoutContainer.addView(this)
        }


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