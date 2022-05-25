package com.swein.androidkotlintool.main.examples.conceptanimationexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import java.lang.ref.WeakReference

class ConceptPageExampleActivity : AppCompatActivity() {

    private val textView: TextView by lazy {
        findViewById(R.id.textView)
    }

    private val viewPager2: ViewPager2 by lazy {
        findViewById(R.id.viewPager2)
    }

    private val lottieAnimationView: LottieAnimationView by lazy {
        findViewById(R.id.lottieAnimationView)
    }
    private var totalFrames = 0f


    private val animationList = mutableListOf<Int>()

    private lateinit var adapter: ConceptPageExampleAdapter

    private val viewPager2OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        var currentIndex = -1

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)

            // positionOffset is from 0 to 1
            // 3 pages for 2 scroll times

            val currentOffset = positionOffset + position

            val currentFrame = (currentOffset * 0.5 * totalFrames).toInt()

            ILog.debug("???", "currentOffset $currentOffset, currentFrame $currentFrame")

            if (currentFrame > 0) {

                lottieAnimationView.setMinAndMaxFrame(currentFrame, currentFrame + 1)
                lottieAnimationView.playAnimation()
            }
        }

        override fun onPageSelected(position: Int) {
            currentIndex = position
        }

        @SuppressLint("SetTextI18n")
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)

            if (state == ViewPager.SCROLL_STATE_IDLE) {

                textView.text = "${currentIndex + 1}/${animationList.size}"

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concept_page_example)

        lottieAnimationView.addLottieOnCompositionLoadedListener { lottieComposition ->
            ILog.debug("???", "total frames: ${lottieComposition.durationFrames}")

            totalFrames = lottieComposition.durationFrames
        }

        lottieAnimationView.setAnimation(R.raw.concept_animation)

        initViewPager2()
    }

    private fun initViewPager2() {

        animationList.clear()

        animationList.add(R.raw.concept_animation_1)
        animationList.add(R.raw.concept_animation_2)
        animationList.add(R.raw.concept_animation_3)

        adapter = ConceptPageExampleAdapter()

        viewPager2.adapter = adapter
        viewPager2.clipChildren = false
        viewPager2.clipToPadding = false
        viewPager2.offscreenPageLimit = 3

        adapter.reload(animationList)

        viewPager2.registerOnPageChangeCallback(viewPager2OnPageChangeCallback)

        viewPager2.currentItem = 0
    }

    override fun onDestroy() {
        viewPager2.unregisterOnPageChangeCallback(viewPager2OnPageChangeCallback)
        super.onDestroy()
    }
}

class ConceptPageExampleAdapter : RecyclerView.Adapter<ConceptPageExampleItemViewHolder>() {

    private val list = mutableListOf<Int>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConceptPageExampleItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_concept_page_example_item, parent, false)
        return ConceptPageExampleItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConceptPageExampleItemViewHolder, position: Int) {
        holder.imageResource = list[position]
        holder.updateView()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reload(list: MutableList<Int>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class ConceptPageExampleItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val view: WeakReference<View> = WeakReference(itemView)

    private var lottieAnimationView: LottieAnimationView? = null

    var imageResource = 0

    init {
        findView()
    }

    private fun findView() {

        view.get()?.let {
            lottieAnimationView = it.findViewById(R.id.lottieAnimationView)
        }
    }

    fun updateView() {

        lottieAnimationView?.setAnimation(imageResource)
        lottieAnimationView?.repeatMode
    }
}