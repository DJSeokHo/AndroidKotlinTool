package com.swein.androidkotlintool.main.examples.multipleviewtypesrecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.multipleviewtypesrecyclerview.adapter.MultipleViewTypesAdapter

class MultipleViewTypesRecyclerViewExampleActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var multipleViewTypesAdapter: MultipleViewTypesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_view_types_recycler_view_example)

        findView()
        initList()

        reload()
    }

    private fun findView() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        recyclerView = findViewById(R.id.recyclerView)
    }

    private fun initList() {

        layoutManager = LinearLayoutManager(this)
        multipleViewTypesAdapter = MultipleViewTypesAdapter()
        multipleViewTypesAdapter.onLoadMore = {
            loadMore()
        }

        swipeRefreshLayout.setOnRefreshListener {

            reload()

            swipeRefreshLayout.isRefreshing = false
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = multipleViewTypesAdapter

    }

    private fun reload() {

        recyclerView.post {
            multipleViewTypesAdapter.reload(createTempData(0, 15))
        }
    }

    private fun loadMore() {

        recyclerView.post {
            multipleViewTypesAdapter.loadMore(createTempData(multipleViewTypesAdapter.itemCount, 10))
        }
    }

    private fun createTempData(offset: Int, limit: Int): MutableList<String> {
        val list: MutableList<String> = mutableListOf()

        for(i in offset..(offset + limit)) {

            // create random content
            when ((0..2).random()) {
                0 -> {
                    list.add("Type A")
                }
                1 -> {
                    list.add("Type B")
                }
                2 -> {
                    list.add("Type C")
                }
            }
        }

        return list
    }
}