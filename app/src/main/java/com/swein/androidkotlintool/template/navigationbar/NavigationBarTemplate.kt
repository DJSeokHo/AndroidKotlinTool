package com.swein.androidkotlintool.template.navigationbar

import android.content.Context
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.views.ViewUtil

class NavigationBarTemplate {

    interface NavigationBarTemplateDelegate {
        fun onLeftButtonClick(navigationBarTemplate: NavigationBarTemplate)
        fun onRightButtonClick(navigationBarTemplate: NavigationBarTemplate)
    }

    companion object {
        private const val TAG = "NavigationBarTemplate"
    }

    private var view: View? = null
    private var navigationBarTemplateDelegate: NavigationBarTemplateDelegate? = null

    private var textViewTitle: TextView? = null
    private var imageButtonLeft: ImageButton? = null
    private var imageButtonRight: ImageButton? = null

    constructor(context: Context) {
        view = ViewUtil.viewLayoutInflater(context, R.layout.view_navigation_bar_template, null, false)
        findView()
        setListener()
    }

    fun setDelegate(navigationbarTemplateDelegate: NavigationBarTemplateDelegate): NavigationBarTemplate {
        this.navigationBarTemplateDelegate = navigationbarTemplateDelegate
        return this
    }

    private fun findView() {
        textViewTitle = view?.findViewById(R.id.textViewTitle)
        imageButtonLeft = view?.findViewById(R.id.imageButtonLeft)
        imageButtonRight = view?.findViewById(R.id.imageButtonRight)
    }
    private fun setListener() {
        imageButtonLeft?.setOnClickListener {
            navigationBarTemplateDelegate?.onLeftButtonClick(this)
        }

        imageButtonRight?.setOnClickListener {
            navigationBarTemplateDelegate?.onRightButtonClick(this)
        }
    }

    fun setTitle(title: String): NavigationBarTemplate {
        textViewTitle?.text = title
        textViewTitle?.visibility = View.VISIBLE
        return this
    }
    fun hideTitle(): NavigationBarTemplate {
        textViewTitle?.visibility = View.GONE
        return this
    }

    fun setLeftButton(resourceId: Int): NavigationBarTemplate {
        imageButtonLeft?.setImageResource(resourceId)
        imageButtonLeft?.visibility = View.VISIBLE
        return this
    }
    fun showLeftButton() {
        imageButtonLeft?.visibility = View.VISIBLE
    }
    fun hideLeftButton(): NavigationBarTemplate {
        imageButtonLeft?.visibility = View.GONE
        return this
    }

    fun setRightButton(resourceId: Int): NavigationBarTemplate {
        imageButtonRight?.setImageResource(resourceId)
        return this
    }
    fun showRightButton() {
        imageButtonRight?.visibility = View.VISIBLE
    }
    fun hideRightButton(): NavigationBarTemplate {
        imageButtonRight?.visibility = View.GONE
        return this
    }

    fun getView(): View? {
        return view
    }

}