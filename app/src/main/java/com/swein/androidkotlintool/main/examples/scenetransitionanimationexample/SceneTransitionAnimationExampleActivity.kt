package com.swein.androidkotlintool.main.examples.scenetransitionanimationexample

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.scenetransitionanimationexample.adapter.SceneTransitionAnimationAdapter
import com.swein.androidkotlintool.main.examples.scenetransitionanimationexample.adapter.item.SceneTransitionAnimationItemModel
import com.swein.androidkotlintool.main.examples.scenetransitionanimationexample.detail.SceneTransitionAnimationDetailExampleActivity

class SceneTransitionAnimationExampleActivity : AppCompatActivity() {

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    private val textView: TextView by lazy {
        findViewById(R.id.textView)
    }

    private lateinit var adapter: SceneTransitionAnimationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scene_transition_animation_example)

        initList()
        initBottomPart()
    }

    private fun initList() {

        adapter = SceneTransitionAnimationAdapter { model, imageView, textView ->

            SceneTransitionAnimationDetailExampleActivity.start(this, imageView, textView, Bundle().apply {
                putInt("imageResource", model.imageResource)
                putString("contentString", model.contentString)
            })

        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val modelList = mutableListOf<SceneTransitionAnimationItemModel>()
        for (i in 0 until 100) {
            modelList.add(SceneTransitionAnimationItemModel().apply {
                imageResource = R.drawable.coding_with_cat
                contentString = "position $i"
            })
        }

        adapter.reload(modelList)
    }

    private fun initBottomPart() {

        val bottomModel = SceneTransitionAnimationItemModel().apply {
            imageResource = R.drawable.coding_with_cat
            contentString = "Coding with cat"
        }

        textView.text = bottomModel.contentString
        imageView.setImageResource(bottomModel.imageResource)

        imageView.setOnClickListener {

            SceneTransitionAnimationDetailExampleActivity.start(this, imageView, textView, Bundle().apply {
                putInt("imageResource", bottomModel.imageResource)
                putString("contentString", bottomModel.contentString)
            })
        }
    }
}

