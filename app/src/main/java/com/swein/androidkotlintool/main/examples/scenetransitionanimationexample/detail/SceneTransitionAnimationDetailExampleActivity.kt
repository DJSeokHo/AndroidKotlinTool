package com.swein.androidkotlintool.main.examples.scenetransitionanimationexample.detail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import com.swein.androidkotlintool.R

class SceneTransitionAnimationDetailExampleActivity : AppCompatActivity() {

    companion object {

        // View name of the header image. Used for activity scene transitions
        private const val SCENE_TRANSITIONS_IMAGE_VIEW = "SceneTransitionAnimationDetailExampleActivity:imageView"

        // View name of the header title. Used for activity scene transitions
        private const val SCENE_TRANSITIONS_TEXT_VIEW = "SceneTransitionAnimationDetailExampleActivity:textView"


        fun start(activity: Activity, imageView: ImageView, textView: TextView, bundle: Bundle? = null) {

            val pairs = listOf<Pair<View, String>>(
                Pair(imageView, SCENE_TRANSITIONS_IMAGE_VIEW),
                Pair(textView, SCENE_TRANSITIONS_TEXT_VIEW)
            )

            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,  // Now we provide a list of Pair items which contain the view we can transitioning
                // from, and the name of the view it is transitioning to, in the launched activity
                *pairs.toTypedArray()
            )

            Intent(activity, SceneTransitionAnimationDetailExampleActivity::class.java).apply {

                bundle?.let {
                    putExtra("bundle", bundle)
                }

                activity.startActivity(this, option.toBundle())
            }

        }

    }

    private val imageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    private val textView: TextView by lazy {
        findViewById(R.id.textView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_scene_transition_animation_detail_example)

        addTransitionListener()

        ViewCompat.setTransitionName(imageView, SCENE_TRANSITIONS_IMAGE_VIEW)
        ViewCompat.setTransitionName(textView, SCENE_TRANSITIONS_TEXT_VIEW)

        intent.getBundleExtra("bundle")?.let {

            imageView.setImageResource(it.getInt("imageResource"))
            textView.text = it.getString("contentString")
        }
    }

    private fun addTransitionListener(): Boolean {
        val transition = window.sharedElementEnterTransition
        if (transition != null) {

            transition.addListener(object : Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition) {
                    // transition has ended, we can change UI here

                    textView.setTextColor(Color.RED)

                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this)
                }

                override fun onTransitionStart(transition: Transition) {}

                override fun onTransitionCancel(transition: Transition) {
                    transition.removeListener(this)
                }

                override fun onTransitionPause(transition: Transition) {
                }

                override fun onTransitionResume(transition: Transition) {
                }
            })
            return true
        }

        return false
    }
}