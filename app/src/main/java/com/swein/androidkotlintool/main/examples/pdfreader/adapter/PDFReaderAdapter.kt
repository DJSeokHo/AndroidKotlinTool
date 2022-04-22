package com.swein.androidkotlintool.main.examples.pdfreader.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.pdfreader.adapter.item.PDFReaderItemViewHolder

class PDFReaderAdapter(
    val onLoadMore: () -> Unit
): RecyclerView.Adapter<PDFReaderItemViewHolder>() {

    private var list = mutableListOf<Bitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PDFReaderItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_pdf_view_item, parent, false)
        return PDFReaderItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PDFReaderItemViewHolder, position: Int) {

        holder.bitmap = list[position]
        holder.page = position
        holder.updateView()

        if (position == list.size - 1) {
            onLoadMore()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reload(list: List<Bitmap>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: List<Bitmap>) {
        this.list.addAll(list)
        notifyItemRangeChanged(this.list.size - list.size + 1, list.size)
    }
}