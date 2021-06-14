package com.swein.androidkotlintool.main.examples.materialdesigntutorial.daythree

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import kotlin.math.abs

class MDDayThreeActivity : AppCompatActivity() {

    private val appBarLayout: AppBarLayout  by lazy {
        findViewById(R.id.appBarLayout)
    }

    private val toolbar: Toolbar by lazy {
        findViewById(R.id.toolbar)
    }

    private val viewCover: View by lazy {
        findViewById(R.id.viewCover)
    }

    private val floatingActionButton: FloatingActionButton by lazy {
        findViewById(R.id.floatingActionButton)
    }

    private val collapsingToolbarLayout: CollapsingToolbarLayout by lazy {
        findViewById(R.id.collapsingToolbarLayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mdday_three)

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener {
                appBarLayout, verticalOffset ->

            val offset = abs(verticalOffset)
            val scrollRange = appBarLayout.totalScrollRange

            val alpha = offset.toFloat() / scrollRange.toFloat()
            viewCover.alpha = alpha
            ILog.debug("???", "$offset $scrollRange $alpha")
        })

        floatingActionButton.setOnClickListener {
            Toast.makeText(this@MDDayThreeActivity, "floating action button click", Toast.LENGTH_SHORT).show()
        }

        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#eeeeee"))

        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.mipmap.ic_launcher)
        toolbar.title = "title"
        toolbar.inflateMenu(R.menu.toolbar_menu)

        toolbar.setNavigationOnClickListener {
            Toast.makeText(this@MDDayThreeActivity, "navigation click", Toast.LENGTH_SHORT).show()
        }

        toolbar.setOnMenuItemClickListener {

            when (it.itemId) {

                R.id.menuOne -> {
                    Toast.makeText(this@MDDayThreeActivity, "menu one", Toast.LENGTH_SHORT).show()
                }

                R.id.menuTwo -> {
                    Toast.makeText(this@MDDayThreeActivity, "menu two", Toast.LENGTH_SHORT).show()
                }

                R.id.menuThree -> {
                    Toast.makeText(this@MDDayThreeActivity, "menu three", Toast.LENGTH_SHORT).show()
                }
            }

            true
        }

    }
}