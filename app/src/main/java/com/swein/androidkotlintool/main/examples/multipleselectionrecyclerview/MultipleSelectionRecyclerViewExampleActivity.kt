package com.swein.androidkotlintool.main.examples.multipleselectionrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.switchmaterial.SwitchMaterial
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.multipleselectionrecyclerview.adapter.MultipleSelectionRecyclerViewAdapter
import com.swein.androidkotlintool.main.examples.multipleselectionrecyclerview.adapter.item.MultipleSelectionRecyclerViewItemData
import com.swein.androidkotlintool.main.examples.multipleviewtypesrecyclerview.adapter.MultipleViewTypesAdapter
import java.util.*

class MultipleSelectionRecyclerViewExampleActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: MultipleSelectionRecyclerViewAdapter

    private lateinit var switchMaterial: SwitchMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_selection_recycler_view_example)

        findView()
        setListener()
        initList()
        reload()
    }

    private fun findView() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        recyclerView = findViewById(R.id.recyclerView)
        switchMaterial = findViewById(R.id.switchMaterial)
    }

    private fun setListener() {
        switchMaterial.setOnCheckedChangeListener { _, isChecked ->
            adapter.toggleSelection(isChecked)
        }
    }

    private fun initList() {

        layoutManager = LinearLayoutManager(this)
        adapter = MultipleSelectionRecyclerViewAdapter()
        adapter.onLoadMore = {
            loadMore()
        }

        swipeRefreshLayout.setOnRefreshListener {

            reload()

            swipeRefreshLayout.isRefreshing = false
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

    }

    private fun reload() {

        recyclerView.post {
            adapter.reload(createTempData(0, 15))
        }
    }

    private fun loadMore() {

        recyclerView.post {
            adapter.loadMore(createTempData(adapter.itemCount, 10))
        }
    }

    private fun createTempData(offset: Int, limit: Int): MutableList<MultipleSelectionRecyclerViewItemData> {
        val list: MutableList<MultipleSelectionRecyclerViewItemData> = mutableListOf()

        var data: MultipleSelectionRecyclerViewItemData
        for(i in offset..(offset + limit)) {

            data = MultipleSelectionRecyclerViewItemData(id = UUID.randomUUID().toString().replace("-", ""), content = "content index $i")
            list.add(data)
        }

        return list
    }
}