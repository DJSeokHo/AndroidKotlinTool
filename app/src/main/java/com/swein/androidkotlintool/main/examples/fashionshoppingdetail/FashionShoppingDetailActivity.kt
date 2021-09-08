package com.swein.androidkotlintool.main.examples.fashionshoppingdetail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.display.DisplayUtility

class FashionShoppingDetailActivity : AppCompatActivity() {

    private lateinit var shapeableImageView: ShapeableImageView
    private lateinit var textViewCategory: TextView
    private lateinit var textViewName: TextView
    private lateinit var textViewPrice: TextView
    private lateinit var textViewDescription: TextView

    private lateinit var textViewS: TextView
    private lateinit var textViewM: TextView
    private lateinit var textViewL: TextView
    private lateinit var textViewXL: TextView
    private lateinit var textViewXXL: TextView

    private lateinit var buttonContinue: Button

    private lateinit var imageButtonMenu: ImageButton
    private lateinit var imageButtonCart: ImageButton
    private lateinit var imageButtonLike: ImageButton

    private var isLiked = false
    private var isCart = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fashion_shopping_detail)

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        findView()
        setListener()

        updateView()
    }

    private fun findView() {

        imageButtonMenu = findViewById(R.id.imageButtonMenu)
        imageButtonCart = findViewById(R.id.imageButtonCart)
        imageButtonLike = findViewById(R.id.imageButtonLike)

        shapeableImageView = findViewById(R.id.shapeableImageView)
        textViewCategory = findViewById(R.id.textViewCategory)
        textViewName = findViewById(R.id.textViewName)
        textViewPrice = findViewById(R.id.textViewPrice)
        textViewDescription = findViewById(R.id.textViewDescription)
        textViewS = findViewById(R.id.textViewS)
        textViewM = findViewById(R.id.textViewM)
        textViewL = findViewById(R.id.textViewL)
        textViewXL = findViewById(R.id.textViewXL)
        textViewXXL = findViewById(R.id.textViewXXL)
        buttonContinue = findViewById(R.id.buttonContinue)

        val radius = DisplayUtility.dipToPx(this, 36f)
        shapeableImageView.shapeAppearanceModel = shapeableImageView.shapeAppearanceModel.toBuilder()
            .setBottomLeftCorner(CornerFamily.ROUNDED, radius.toFloat())
            .build()
    }

    private fun setListener() {

        imageButtonMenu.setOnClickListener {
            Toast.makeText(this@FashionShoppingDetailActivity, "click menu", Toast.LENGTH_SHORT).show()
        }
        imageButtonCart.setOnClickListener {

            isCart = !isCart
            toggleCart()
        }
        imageButtonLike.setOnClickListener {

            isLiked = !isLiked
            toggleLike()
        }


        textViewS.setOnClickListener {
            resetSize()
            it.setBackgroundResource(R.drawable.size_selection_bg)
        }
        textViewM.setOnClickListener {
            resetSize()
            it.setBackgroundResource(R.drawable.size_selection_bg)
        }
        textViewL.setOnClickListener {
            resetSize()
            it.setBackgroundResource(R.drawable.size_selection_bg)
        }
        textViewXL.setOnClickListener {
            resetSize()
            it.setBackgroundResource(R.drawable.size_selection_bg)
        }
        textViewXXL.setOnClickListener {
            resetSize()
            it.setBackgroundResource(R.drawable.size_selection_bg)
        }

    }

    private fun resetSize() {
        textViewS.setBackgroundResource(R.drawable.size_un_selection_bg)
        textViewM.setBackgroundResource(R.drawable.size_un_selection_bg)
        textViewL.setBackgroundResource(R.drawable.size_un_selection_bg)
        textViewXL.setBackgroundResource(R.drawable.size_un_selection_bg)
        textViewXXL.setBackgroundResource(R.drawable.size_un_selection_bg)
    }

    private fun updateView() {

        Glide.with(this).asBitmap().load(R.drawable.dom_hill)
            .transition(BitmapTransitionOptions.withCrossFade())
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(shapeableImageView)

        textViewCategory.text = "Sweater"
        textViewName.text = "Summer look short sweater"
        textViewPrice.text = "$260"
        textViewDescription.text = "description about summer look short sweater, description about summer look short sweater, description about summer look short sweater, description about summer look short sweater,"
//        textViewS = findViewById(R.id.textViewS)
//        textViewM = findViewById(R.id.textViewM)
//        textViewL = findViewById(R.id.textViewL)
//        textViewXL = findViewById(R.id.textViewXL)
//        textViewXXL = findViewById(R.id.textViewXXL)

    }

    private fun toggleLike() {

        if (isLiked) {
            imageButtonLike.setImageResource(R.drawable.like_on)
        }
        else {
            imageButtonLike.setImageResource(R.drawable.like_off)
        }
    }

    private fun toggleCart() {
        if (isCart) {
            imageButtonCart.setImageResource(R.drawable.cart_on)
        }
        else {
            imageButtonCart.setImageResource(R.drawable.cart_off)
        }
    }
}