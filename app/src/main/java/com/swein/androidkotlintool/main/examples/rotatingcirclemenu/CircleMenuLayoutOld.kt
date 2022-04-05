package com.swein.androidkotlintool.main.examples.rotatingcirclemenu

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.swein.androidkotlintool.R
import kotlin.math.abs

class CircleMenuLayoutOld(context: Context?, attrs: AttributeSet?) :
    ViewGroup(context, attrs) {
    private var mRadius = 0

    /**
     * 菜单的中心child的默认尺寸
     */
    private val RADIO_DEFAULT_CENTERITEM_DIMENSION = 1 / 3f

    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private var mFlingableValue = FLINGABLE_VALUE

    /**
     * 该容器的内边距,无视padding属性，如需边距请用该变量
     */
    private var mPadding = 0f

    /**
     * 布局时的开始角度
     */
    private var mStartAngle = 0.0

    /**
     * 菜单项的文本
     */
    private lateinit var mItemTexts: List<String>

    /**
     * 菜单项的图标
     */
    private lateinit var mItemImgs: List<Int>

    /**
     * 菜单的个数
     */
    private var mMenuItemCount = 0

    /**
     * 检测按下到抬起时旋转的角度
     */
    private var mTmpAngle = 0f

    /**
     * 检测按下到抬起时使用的时间
     */
    private var mDownTime: Long = 0

    /**
     * 判断是否正在自动滚动
     */
    private var isFling = false
    private var mMenuItemLayoutId = R.layout.circle_menu_item

    /**
     * 设置布局的宽高，并策略menu item宽高
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var resWidth = 0
        var resHeight = 0

        /**
         * 根据传入的参数，分别获取测量模式和测量值
         */
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        /**
         * 如果宽或者高的测量模式非精确值
         */
        if (widthMode != MeasureSpec.EXACTLY
            || heightMode != MeasureSpec.EXACTLY
        ) {
            // 主要设置为背景图的高度
            resWidth = suggestedMinimumWidth
            // 如果未设置背景图片，则设置为屏幕宽高的默认值
            resWidth = if (resWidth == 0) defaultWidth else resWidth
            resHeight = suggestedMinimumHeight
            // 如果未设置背景图片，则设置为屏幕宽高的默认值
            resHeight = if (resHeight == 0) defaultWidth else resHeight
        } else {
            // 如果都设置为精确值，则直接取小值；
            resHeight = Math.min(width, height)
            resWidth = resHeight
        }
        setMeasuredDimension(resWidth, resHeight)

        // 获得半径
        mRadius = Math.max(measuredWidth, measuredHeight)

        // menu item数量
        val count = childCount
        // menu item尺寸
        val childSize = (mRadius * RADIO_DEFAULT_CHILD_DIMENSION).toInt()
        // menu item测量模式
        val childMode = MeasureSpec.EXACTLY

        // 迭代测量
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility == GONE) {
                continue
            }

            // 计算menu item的尺寸；以及和设置好的模式，去对item进行测量
            val makeMeasureSpec = MeasureSpec.makeMeasureSpec(
                childSize,
                childMode
            )
            child.measure(makeMeasureSpec, makeMeasureSpec)
        }
        mPadding = RADIO_PADDING_LAYOUT * mRadius
    }

    /**
     * MenuItem的点击事件接口
     *
     * @author zhy
     */
    interface OnMenuItemClickListener {
        fun itemClick(view: View?, pos: Int)
        fun itemCenterClick(view: View?)
    }

    /**
     * MenuItem的点击事件接口
     */
    private var mOnMenuItemClickListener: OnMenuItemClickListener? = null

    /**
     * 设置MenuItem的点击事件接口
     *
     * @param mOnMenuItemClickListener
     */
    fun setOnMenuItemClickListener(
        mOnMenuItemClickListener: OnMenuItemClickListener?
    ) {
        this.mOnMenuItemClickListener = mOnMenuItemClickListener
    }

    /**
     * 设置menu item的位置
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val layoutRadius = mRadius

        // Laying out the child views
        val childCount = childCount
        var left: Int
        var top: Int
        // menu item 的尺寸
        val cWidth = (layoutRadius * RADIO_DEFAULT_CHILD_DIMENSION).toInt()

        // 根据menu item的个数，计算角度
        val angleDelay = (360 / (getChildCount() - 1)).toFloat()

        // 遍历去设置menuitem的位置
        for (i in 0 until childCount) {
            val child = getChildAt(i)

            if (child.visibility == GONE) {
                continue
            }
            mStartAngle %= 360.0

            // 计算，中心点到menu item中心的距离
            val tmp = layoutRadius / 2f - cWidth / 2 - mPadding

            // tmp cosa 即menu item中心点的横坐标
            left = (layoutRadius
                    / 2
                    + Math.round(
                tmp
                        * Math.cos(Math.toRadians(mStartAngle)) - 1 / 2f
                        * cWidth
            ).toInt())
            // tmp sina 即menu item的纵坐标
            top = (layoutRadius
                    / 2
                    + Math.round(
                tmp
                        * Math.sin(Math.toRadians(mStartAngle)) - 1 / 2f
                        * cWidth
            ).toInt())
            child.layout(left, top, left + cWidth, top + cWidth)
            // 叠加尺寸
            mStartAngle += angleDelay.toDouble()
        }

    }

    /**
     * 记录上一次的x，y坐标
     */
    private var mLastX = 0f
    private var mLastY = 0f

    /**
     * 自动滚动的Runnable
     */
    private var mFlingRunnable: AutoFlingRunnable? = null
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = x
                mLastY = y
                mDownTime = System.currentTimeMillis()
                mTmpAngle = 0f

                // 如果当前已经在快速滚动
                if (isFling) {
                    // 移除快速滚动的回调
                    removeCallbacks(mFlingRunnable)
                    isFling = false
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                /**
                 * 获得开始的角度
                 */
                val start = getAngle(mLastX, mLastY)

                /**
                 * 获得当前的角度
                 */
                val end = getAngle(x, y)

                // Log.e("TAG", "start = " + start + " , end =" + end);
                // 如果是一、四象限，则直接end-start，角度值都是正值
                if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
                    mStartAngle += (end - start).toDouble()
                    mTmpAngle += end - start
                } else  // 二、三象限，色角度值是付值
                {
                    mStartAngle += (start - end).toDouble()
                    mTmpAngle += start - end
                }
                // 重新布局
                requestLayout()
                mLastX = x
                mLastY = y
            }
            MotionEvent.ACTION_UP -> {

                // 计算，每秒移动的角度
                val anglePerSecond = (mTmpAngle * 1000
                        / (System.currentTimeMillis() - mDownTime))

                // Log.e("TAG", anglePrMillionSecond + " , mTmpAngel = " +
                // mTmpAngle);

                // 如果达到该值认为是快速移动
                if (Math.abs(anglePerSecond) > mFlingableValue && !isFling) {
                    // post一个任务，去自动滚动
                    post(AutoFlingRunnable(anglePerSecond).also {
                        mFlingRunnable = it
                    })
                    return true
                }

                // 如果当前旋转角度超过NOCLICK_VALUE屏蔽点击
                if (Math.abs(mTmpAngle) > NOCLICK_VALUE) {
                    return true
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    /**
     * 主要为了action_down时，返回true
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return true
    }

    /**
     * 根据触摸的位置，计算角度
     *
     * @param xTouch
     * @param yTouch
     * @return
     */
    private fun getAngle(xTouch: Float, yTouch: Float): Float {
        val x = xTouch - mRadius / 2.0
        val y = yTouch - mRadius / 2.0
        return (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI).toFloat()
    }

    /**
     * 根据当前位置计算象限
     *
     * @param x
     * @param y
     * @return
     */
    private fun getQuadrant(x: Float, y: Float): Int {
        val tmpX = (x - mRadius / 2).toInt()
        val tmpY = (y - mRadius / 2).toInt()
        return if (tmpX >= 0) {
            if (tmpY >= 0) 4 else 1
        } else {
            if (tmpY >= 0) 3 else 2
        }
    }

    /**
     * 设置菜单条目的图标和文本
     *
     * @param resIds
     */
    fun setMenuItemIconsAndTexts(resIds: List<Int>, texts: List<String>) {
        mItemImgs = resIds
        mItemTexts = texts

        // 参数检查
        require(!(resIds == null && texts == null)) { "菜单项文本和图片至少设置其一" }

        // 初始化mMenuCount
        mMenuItemCount = resIds?.size ?: texts!!.size
        if (resIds != null && texts != null) {
            mMenuItemCount = Math.min(resIds.size, texts.size)
        }
        addMenuItems()
    }

    /**
     * 设置MenuItem的布局文件，必须在setMenuItemIconsAndTexts之前调用
     *
     * @param mMenuItemLayoutId
     */
    fun setMenuItemLayoutId(mMenuItemLayoutId: Int) {
        this.mMenuItemLayoutId = mMenuItemLayoutId
    }

    /**
     * 添加菜单项
     */
    private fun addMenuItems() {
        val mInflater = LayoutInflater.from(context)
        /**
         * 根据用户设置的参数，初始化view
         */
        for (i in 0 until mMenuItemCount) {
            val view = mInflater.inflate(mMenuItemLayoutId, this, false)
            val iv = view
                .findViewById<View>(R.id.imageView) as ImageView
            val tv = view
                .findViewById<View>(R.id.textView) as TextView
            if (iv != null) {
                iv.visibility = VISIBLE
                iv.setImageResource(mItemImgs!![i])
                iv.setOnClickListener { v ->
                    if (mOnMenuItemClickListener != null) {
                        mOnMenuItemClickListener!!.itemClick(v, i)
                    }
                }
            }
            if (tv != null) {
                tv.visibility = VISIBLE
                tv.text = mItemTexts!![i]
            }

            // 添加view到容器中
            addView(view)
        }
    }

    /**
     * 如果每秒旋转角度到达该值，则认为是自动滚动
     *
     * @param mFlingableValue
     */
    fun setFlingableValue(mFlingableValue: Int) {
        this.mFlingableValue = mFlingableValue
    }

    /**
     * 设置内边距的比例
     *
     * @param mPadding
     */
    fun setPadding(mPadding: Float) {
        this.mPadding = mPadding
    }

    /**
     * 获得默认该layout的尺寸
     *
     * @return
     */
    private val defaultWidth: Int
        private get() {
            val wm = context.getSystemService(
                Context.WINDOW_SERVICE
            ) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return Math.min(outMetrics.widthPixels, outMetrics.heightPixels)
        }

    /**
     * 自动滚动的任务
     *
     * @author zhy
     */
    private inner class AutoFlingRunnable(private var angelPerSecond: Float) : Runnable {
        override fun run() {
            // 如果小于20,则停止
            if (abs(angelPerSecond).toInt() < 20) {
                isFling = false
                return
            }
            isFling = true
            // 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
            mStartAngle += (angelPerSecond / 30).toDouble()
            // 逐渐减小这个值
            angelPerSecond /= 1.0666f
            postDelayed(this, 30)
            // 重新布局
            requestLayout()
        }
    }

    companion object {
        /**
         * 该容器内child item的默认尺寸
         */
        private const val RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f

        /**
         * 该容器的内边距,无视padding属性，如需边距请用该变量
         */
        private const val RADIO_PADDING_LAYOUT = 1 / 12f

        /**
         * 当每秒移动角度达到该值时，认为是快速移动
         */
        private const val FLINGABLE_VALUE = 300

        /**
         * 如果移动角度达到该值，则屏蔽点击
         */
        private const val NOCLICK_VALUE = 3
    }

    init {
        // 无视padding
        setPadding(0, 0, 0, 0)
    }
}
