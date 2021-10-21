package com.swein.androidkotlintool.main.examples.grouprecyclerview

import android.content.Context
import android.graphics.*
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.framework.util.display.DisplayUtility
import com.swein.androidkotlintool.main.examples.grouprecyclerview.adapter.item.GroupItemHeaderViewHolder
import com.swein.androidkotlintool.main.examples.grouprecyclerview.adapter.item.GroupItemModel


class GroupItemDecoration(private val context: Context, private val getGroupItemModelList: () -> MutableList<GroupItemModel>): RecyclerView.ItemDecoration() {

    private val dividerHeight = DisplayUtility.dipToPx(context, 0.8f)
    private val dividerPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.color = Color.parseColor("#ff0000")
    }

    private val sectionItemWidth: Int by lazy {
        DisplayUtility.getScreenWidth(context)
    }

    private val sectionItemHeight: Int by lazy {
        DisplayUtility.dipToPx(context, 33f)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val layoutManager = parent.layoutManager

        // just allow linear layout manager
        if (layoutManager !is LinearLayoutManager) {
           return
        }
        // just allow vertical orientation
        if (LinearLayoutManager.VERTICAL != layoutManager.orientation) {
            return
        }

        val position = parent.getChildAdapterPosition(view)

        val list = getGroupItemModelList()
        if (list.isEmpty()) {
            return
        }

        if (0 == position) {
            /*
            if the item is first item
            then should add a section
             */
            outRect.top = sectionItemHeight

            return
        }

        // if position > 0
        val currentModel = getGroupItemModelList()[position]
        val previousModel = getGroupItemModelList()[position - 1]

        if (currentModel.date != previousModel.date) {
            /*
           if the target value of current item is not same as
           the target value of previous item
           then should add a section
            */
            outRect.top = sectionItemHeight
        }
        else {
            // or add a divider line
            outRect.top = dividerHeight
        }
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val childCount = parent.childCount
        for (i in 0 until childCount) {

            val childView: View = parent.getChildAt(i)
            val position: Int = parent.getChildAdapterPosition(childView)
            val groupItemModel: GroupItemModel = getGroupItemModelList()[position]

            if (getGroupItemModelList().isNotEmpty() &&
                (0 == position || groupItemModel.date != getGroupItemModelList()[position - 1].date)) {

                val top = childView.top - sectionItemHeight
                drawSectionView(c, groupItemModel.date, top)
            }
            else {
                drawDivider(c, childView)
            }

        }

    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val list = getGroupItemModelList()

        if (list.isEmpty()) {
            return
        }

        val childCount = parent.childCount
        if (childCount == 0) {
            return
        }

        //sticky效果其实就是处理第一个itemView，然后让悬浮内容置于第一个itemView之上。
        val firstView = parent.getChildAt(0)

        val position = parent.getChildAdapterPosition(firstView)
        val text = list[position].date
        val groupItemModel: GroupItemModel = list[position]
        val condition = groupItemModel.date != getGroupItemModelList()[position + 1].date

        if (firstView.bottom <= sectionItemHeight && condition) {
            drawSectionView(c, text, firstView.bottom - sectionItemHeight)
        } else {
            drawSectionView(c, text, 0)
        }
    }

    private fun getViewGroupBitmap(viewGroup: ViewGroup): Bitmap {

        val layoutParams = ViewGroup.LayoutParams(sectionItemWidth, sectionItemHeight)
        viewGroup.layoutParams = layoutParams
        viewGroup.measure(
            View.MeasureSpec.makeMeasureSpec(sectionItemWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(sectionItemHeight, View.MeasureSpec.EXACTLY)
        )
        viewGroup.layout(0, 0, sectionItemWidth, sectionItemHeight)

        val bitmap = Bitmap.createBitmap(viewGroup.width, viewGroup.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        viewGroup.draw(canvas)

        return bitmap
    }

    private fun drawSectionView(canvas: Canvas, text: String, top: Int) {

        val view = GroupItemHeaderViewHolder(context)
        view.setDate(text)

        val bitmap = getViewGroupBitmap(view)
        val bitmapCanvas = Canvas(bitmap)
        view.draw(bitmapCanvas)

        canvas.drawBitmap(bitmap, 0f, top.toFloat(), null)
    }

    private fun drawDivider(canvas: Canvas, childView: View) {

        canvas.drawRect(
            0f, // left
            (childView.top - dividerHeight).toFloat(), // top
            childView.right.toFloat(), // right
            childView.top.toFloat(), // bottom
            dividerPaint
        )

    }
}