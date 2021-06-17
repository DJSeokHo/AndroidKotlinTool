package com.swein.androidkotlintool.main.examples.coordinatorlayoutexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.thread.ThreadUtility
import com.swein.androidkotlintool.main.examples.coordinatorlayoutexample.adapter.CoordinatorLayoutExampleAdapter

class CoordinatorLayoutExampleActivity : AppCompatActivity() {

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        findViewById(R.id.swipeRefreshLayout)
    }

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }
    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }
    private val adapter: CoordinatorLayoutExampleAdapter by lazy {
        CoordinatorLayoutExampleAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_layout_example)

        initList()
        reload()
    }

    private fun initList() {

        swipeRefreshLayout.setOnRefreshListener {

            reload()

            swipeRefreshLayout.isRefreshing = false
        }

        adapter.onLoadMore = {

            ThreadUtility.startUIThread(0) {
                loadMore()
            }
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

    }

    private fun reload() {

        adapter.reload(createTempData(0, 10))
    }

    private fun loadMore() {

        adapter.loadMore(createTempData(adapter.itemCount, 10))
    }

    private fun createTempData(offset: Int, limit: Int): MutableList<String> {
        val list: MutableList<String> = mutableListOf()

        for(i in offset..(offset + limit)) {
            list.add("title $i")
        }

        return list
    }
}