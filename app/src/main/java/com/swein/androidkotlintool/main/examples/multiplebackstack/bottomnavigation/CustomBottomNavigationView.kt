package com.swein.androidkotlintool.main.examples.multiplebackstack.bottomnavigation

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ImageViewCompat
import com.swein.androidkotlintool.R

@SuppressLint("ViewConstructor")
class CustomBottomNavigationView(
    context: Context,
    private val onHomeClick: () -> Unit,
    private val onSearchClick: () -> Unit,
    private val onProfileClick: () -> Unit,
): ConstraintLayout(context) {

    companion object {
        const val HOME = "Home"
        const val SEARCH = "Search"
        const val PROFILE = "Profile"
    }

    private var linearLayoutHome: LinearLayout
    private var linearLayoutSearch: LinearLayout
    private var linearLayoutProfile: LinearLayout
    private var textViewHome: TextView
    private var textViewSearch: TextView
    private var textViewProfile: TextView

    private var imageViewHome: ImageView
    private var imageViewSearch: ImageView
    private var imageViewProfile: ImageView

    private var currentTag = HOME

    init {

        inflate(context, R.layout.view_custom_bottom_navigation, this)

        linearLayoutHome = findViewById(R.id.linearLayoutHome)
        linearLayoutSearch = findViewById(R.id.linearLayoutSearch)
        linearLayoutProfile = findViewById(R.id.linearLayoutProfile)

        textViewHome = findViewById(R.id.textViewHome)
        textViewSearch = findViewById(R.id.textViewSearch)
        textViewProfile = findViewById(R.id.textViewProfile)

        imageViewHome = findViewById(R.id.imageViewHome)
        imageViewSearch = findViewById(R.id.imageViewSearch)
        imageViewProfile = findViewById(R.id.imageViewProfile)

        linearLayoutHome.setOnClickListener {

            if (currentTag == HOME) {
                return@setOnClickListener
            }

            currentTag = HOME
            onHomeClick()
        }

        linearLayoutSearch.setOnClickListener {

            if (currentTag == SEARCH) {
                return@setOnClickListener
            }

            currentTag = SEARCH
            onSearchClick()
        }

        linearLayoutProfile.setOnClickListener {

            if (currentTag == PROFILE) {
                return@setOnClickListener
            }

            currentTag = PROFILE
            onProfileClick()
        }

        onHomeClick()
    }

    fun update(tag: String) {
        currentTag = tag
        changeTag()
    }

    private fun changeTag() {

        when (currentTag) {
            "Home" -> {
                resetColor()
                textViewHome.setTextColor(Color.WHITE)
                ImageViewCompat.setImageTintList(imageViewHome, ColorStateList.valueOf(Color.WHITE))
            }

            "Search" -> {
                resetColor()
                textViewSearch.setTextColor(Color.WHITE)
                ImageViewCompat.setImageTintList(imageViewSearch, ColorStateList.valueOf(Color.WHITE))
            }

            "Profile" -> {
                resetColor()
                textViewProfile.setTextColor(Color.WHITE)
                ImageViewCompat.setImageTintList(imageViewProfile, ColorStateList.valueOf(Color.WHITE))
            }
        }
    }

    private fun resetColor() {
        textViewHome.setTextColor(Color.parseColor("#666666"))
        textViewSearch.setTextColor(Color.parseColor("#666666"))
        textViewProfile.setTextColor(Color.parseColor("#666666"))
        ImageViewCompat.setImageTintList(imageViewHome, ColorStateList.valueOf(Color.parseColor("#666666")))
        ImageViewCompat.setImageTintList(imageViewSearch, ColorStateList.valueOf(Color.parseColor("#666666")))
        ImageViewCompat.setImageTintList(imageViewProfile, ColorStateList.valueOf(Color.parseColor("#666666")))

    }

}