package com.swein.androidkotlintool.main.examples.pagingexample.adapter.item

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import java.lang.ref.WeakReference

class GithubRepoFooterItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val view = WeakReference(itemView)

    private lateinit var progressBar: ProgressBar
    private lateinit var textviewErrorMassage: TextView
    private lateinit var buttonRetry: Button

    var retry: (() -> Unit)? = null

    init {
        findView()
    }

    private fun findView() {

        view.get()?.let {
            progressBar = it.findViewById(R.id.progressBar)
            textviewErrorMassage = it.findViewById(R.id.textviewErrorMassage)
            buttonRetry = it.findViewById(R.id.buttonRetry)

            setListener()
        }

    }

    private fun setListener() {
        buttonRetry.setOnClickListener {
            retry?.let {
                it()
            }
        }
    }

    fun stateNone() {
        buttonRetry.visibility = View.GONE
        textviewErrorMassage.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    fun stateLoading() {
        buttonRetry.visibility = View.GONE
        textviewErrorMassage.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    fun stateError(message: String) {
        buttonRetry.visibility = View.VISIBLE
        textviewErrorMassage.visibility = View.VISIBLE
        textviewErrorMassage.text = message

        progressBar.visibility = View.GONE
    }
}