package com.swein.androidkotlintool.main.examples.grouprecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.grouprecyclerview.adapter.GroupAdapter
import com.swein.androidkotlintool.main.examples.grouprecyclerview.adapter.item.GroupItemModel

class GroupRecyclerViewActivity : AppCompatActivity() {

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        findViewById(R.id.swipeRefreshLayout)
    }

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    private lateinit var adapter: GroupAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var groupItemDecoration: GroupItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_recycler_view)

        initList()

        reload()
    }

    private fun initList() {

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            reload()
        }

        layoutManager = LinearLayoutManager(this)
        adapter = GroupAdapter {
            loadMore()
        }

        groupItemDecoration = GroupItemDecoration(this) {
            adapter.list
        }

        recyclerView.addItemDecoration(groupItemDecoration)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun reload() {
        val list = dummyData(0, 20)
        recyclerView.post {
            adapter.reload(list)
        }
    }

    private fun loadMore() {
        val list = dummyData(adapter.itemCount, 20)
        recyclerView.post {
            adapter.loadMore(list)
        }
    }

    private fun dummyData(offset: Int, limit: Int): MutableList<GroupItemModel> {
        val list = mutableListOf<GroupItemModel>()

        var groupItemModel: GroupItemModel
        for (i in offset until offset + limit) {

            groupItemModel = when (i) {
                in 0..15 -> {
                    GroupItemModel("title $i", getDummyDateString("01"))
                }
                in 16..30 -> {
                    GroupItemModel("title $i", getDummyDateString("02"))
                }
                else -> {
                    GroupItemModel("title $i", getDummyDateString("03"))
                }
            }

            list.add(groupItemModel)
        }

        return list
    }

    private fun getDummyDateString(day: String): String {
        return "2021-10-$day"
    }
}