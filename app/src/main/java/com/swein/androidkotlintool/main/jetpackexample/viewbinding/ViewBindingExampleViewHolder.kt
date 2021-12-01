package com.swein.androidkotlintool.main.jetpackexample.viewbinding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.swein.androidkotlintool.databinding.ViewHolderViewBindingExampleBinding
import com.swein.androidkotlintool.framework.utility.debug.ILog

class ViewBindingExampleViewHolder(context: Context) {

    companion object {
        private const val TAG = "ViewBindingExampleViewHolder"
    }

    interface ViewBindingExampleViewHolderDelegate {
        fun onClick()
    }

    private var bindingGetter: ViewHolderViewBindingExampleBinding? = null
    private val binding get() = bindingGetter!!

    var delegate: ViewBindingExampleViewHolderDelegate? = null

    init {
        bindingGetter = ViewHolderViewBindingExampleBinding.inflate(LayoutInflater.from(context))
        setListener()
    }

    private fun setListener() {
        bindingGetter?.button?.setOnClickListener {
            delegate?.onClick()
        }
    }

    fun getView(): View {
        return binding.root
    }

    protected fun finalize() {
        bindingGetter = null
        ILog.debug(TAG, "finalize ${bindingGetter == null}")
    }
}