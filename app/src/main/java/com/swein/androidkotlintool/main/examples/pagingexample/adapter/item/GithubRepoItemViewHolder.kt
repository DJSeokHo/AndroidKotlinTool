package com.swein.androidkotlintool.main.examples.pagingexample.adapter.item

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.pagingexample.model.GithubRepoItemModel
import java.lang.ref.WeakReference

class GithubRepoItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val view = WeakReference(itemView)

    private lateinit var textViewName: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var textViewStar: TextView

    lateinit var modelRepo: GithubRepoItemModel

    init {
        findView()
    }

    private fun findView() {

        view.get()?.let {
            textViewName = it.findViewById(R.id.textViewName)
            textViewDescription = it.findViewById(R.id.textViewDescription)
            textViewStar = it.findViewById(R.id.textViewStar)
        }

    }

    fun updateView() {
        textViewName.text = modelRepo.fullName
        textViewDescription.text = modelRepo.description
        textViewStar.text = modelRepo.stars.toString()
    }
}