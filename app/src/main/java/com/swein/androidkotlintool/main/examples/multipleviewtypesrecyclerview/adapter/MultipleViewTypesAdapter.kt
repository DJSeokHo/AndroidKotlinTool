package com.swein.androidkotlintool.main.examples.multipleviewtypesrecyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.multipleviewtypesrecyclerview.adapter.item.TypeAViewHolder
import com.swein.androidkotlintool.main.examples.multipleviewtypesrecyclerview.adapter.item.TypeBViewHolder
import com.swein.androidkotlintool.main.examples.multipleviewtypesrecyclerview.adapter.item.TypeCViewHolder

class MultipleViewTypesAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewHolderType {
        TYPE_A, TYPE_B, TYPE_C
    }

    private var list: MutableList<String> = mutableListOf()

    var onLoadMore: (() -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        return when (p1) {

            ViewHolderType.TYPE_A.ordinal -> {
                val view = LayoutInflater.from(p0.context).inflate(R.layout.view_holder_type_a, p0, false)
                return TypeAViewHolder(view)
            }

            ViewHolderType.TYPE_B.ordinal -> {
                val view = LayoutInflater.from(p0.context).inflate(R.layout.view_holder_type_b, p0, false)
                return TypeBViewHolder(view)
            }

            ViewHolderType.TYPE_C.ordinal -> {
                val view = LayoutInflater.from(p0.context).inflate(R.layout.view_holder_type_c, p0, false)
                return TypeCViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(p0.context).inflate(R.layout.view_holder_type_a, p0, false)
                return TypeAViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            "Type A" -> ViewHolderType.TYPE_A.ordinal
            "Type B" -> ViewHolderType.TYPE_B.ordinal
            "Type C" -> ViewHolderType.TYPE_C.ordinal
            else -> ViewHolderType.TYPE_A.ordinal
        }
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {

        when (p0) {
            is TypeAViewHolder -> {
                p0.contentA = list[p1]
                p0.updateView()
            }
            is TypeBViewHolder -> {
                p0.contentB = list[p1]
                p0.updateView()
            }
            is TypeCViewHolder -> {
                p0.contentC = list[p1]
                p0.updateView()
            }
        }

        if (p1 == list.size - 1) {
            onLoadMore?.let {
                it()
            }
        }
    }

    fun reload(list: MutableList<String>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: MutableList<String>) {
        this.list.addAll(list)
        notifyItemRangeChanged(this.list.size - list.size + 1, list.size)
    }
}