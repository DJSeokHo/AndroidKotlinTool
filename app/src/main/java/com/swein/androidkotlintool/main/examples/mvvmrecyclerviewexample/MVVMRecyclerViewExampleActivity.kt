package com.swein.androidkotlintool.main.examples.mvvmrecyclerviewexample

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.mvvmrecyclerviewexample.adapter.MVVMExampleAdapter
import com.swein.androidkotlintool.main.examples.mvvmrecyclerviewexample.viewmodel.MVVMRecyclerViewExampleViewModel

class MVVMRecyclerViewExampleActivity : AppCompatActivity() {

    companion object {

        const val LIST_LIMIT = 10
    }

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        findViewById(R.id.swipeRefreshLayout)
    }
    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private lateinit var adapter: MVVMExampleAdapter

    private val viewModel: MVVMRecyclerViewExampleViewModel by viewModels()

    private val frameLayoutProgress: FrameLayout by lazy {
        findViewById(R.id.frameLayoutProgress)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvmrecycler_view_example)

        initList()
        initObserver()
        reload()
    }

    private fun initList() {

        swipeRefreshLayout.setOnRefreshListener {

            reload()

            swipeRefreshLayout.isRefreshing = false
        }

        recyclerView.layoutManager = layoutManager

        adapter = MVVMExampleAdapter(viewModel.list)
        adapter.onLoadMore = {
            // load more
            loadMore()
        }

        recyclerView.adapter = adapter
    }

    private fun initObserver() {
        viewModel.list.observe(this) {

            if (it.size <= LIST_LIMIT) {
                adapter.reload()
            }
            else {
                adapter.loadMore()
            }

            ILog.debug("Activity", "total in view model is ${viewModel.list.value?.size}")
        }
    }

    private fun showProgress() {
        frameLayoutProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        frameLayoutProgress.visibility = View.GONE
    }

    private fun reload() {

        viewModel.reload(10, {
            showProgress()
        }, {
            hideProgress()
        })
    }

    private fun loadMore() {

        viewModel.loadMore(adapter.itemCount, 10, {
            showProgress()
        }, {
            hideProgress()
        })
    }

}