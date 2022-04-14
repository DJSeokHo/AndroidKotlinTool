package com.swein.androidkotlintool.main.examples.recyclerviewheaderfooter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.main.examples.recyclerviewheaderfooter.adapter.RHFAdapter

class RecyclerViewWithHeaderAndFooterExampleActivity : AppCompatActivity() {

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        findViewById(R.id.swipeRefreshLayout)
    }

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_with_header_and_footer_example)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            reload()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RHFAdapter {
            loadMore()
        }

        reload()
    }

    private fun reload() {

        recyclerView.post {

            (recyclerView.adapter as RHFAdapter).reload(fetchDummyData(0, 20))

        }

    }

    private fun loadMore() {

        recyclerView.post {

            val adapter = recyclerView.adapter as RHFAdapter

            // not item count, use data count~
            adapter.loadMore(fetchDummyData(adapter.getDataCount(), 15))

        }

    }

    private fun fetchDummyData(offset: Int, limit: Int): List<String> {

        val list = mutableListOf<String>()

        for (i in offset until offset + limit) {

            if (i > 36) {
                break
            }

            list.add("index is $i")
            ILog.debug("???", "i is $i")
        }

        return list
    }
}