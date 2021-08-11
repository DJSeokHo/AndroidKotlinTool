package com.swein.androidkotlintool.main.examples.flowexample

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.flowexample.adapter.FlowExampleAdapter
import com.swein.androidkotlintool.main.examples.flowexample.viewmodel.FlowExampleState
import com.swein.androidkotlintool.main.examples.flowexample.viewmodel.FlowExampleViewModel
import kotlinx.coroutines.flow.collect

class FlowExampleSubActivity : AppCompatActivity() {

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        findViewById(R.id.swipeRefreshLayout)
    }
    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private val frameLayoutProgress: FrameLayout by lazy {
        findViewById(R.id.frameLayoutProgress)
    }

    private val textView: TextView by lazy {
        findViewById(R.id.textView)
    }

    private lateinit var adapter: FlowExampleAdapter

    private val viewModel: FlowExampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow_example_sub)

        initObserver()
        initList()

        reload()

        textView.text = "${viewModel.testValue}"
        findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.testValue++
            textView.text = "${viewModel.testValue}"
        }

    }

    private fun initObserver() {

        lifecycleScope.launchWhenCreated {

//            whenCreated {  }
//            whenStarted {  }
//            whenResumed {  }

//            if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
//
//            }

            viewModel.flowExampleUIState.collect {

                when (it) {

                    is FlowExampleState.Loading -> {
                        showProgress()
                        ILog.debug("!!! loading on ", Thread.currentThread().name)
                    }

                    is FlowExampleState.Reload -> {
                        ILog.debug("!!! reload on ", Thread.currentThread().name)
                        adapter.reload(it.list)
                        hideProgress()
                    }

                    is FlowExampleState.LoadMore -> {
                        ILog.debug("!!! load more on ", Thread.currentThread().name)
                        adapter.loadMore(it.list)
                        hideProgress()
                    }

                    else -> Unit
                }
            }

        }
    }

    private fun initList() {

        swipeRefreshLayout.setOnRefreshListener {

            reload()

            swipeRefreshLayout.isRefreshing = false
        }

        recyclerView.layoutManager = layoutManager

        adapter = FlowExampleAdapter {
            // load more
            loadMore()
        }

        recyclerView.adapter = adapter
    }



    private fun showProgress() {
        frameLayoutProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        frameLayoutProgress.visibility = View.GONE
    }

    private fun reload() {

        ILog.debug("!!!", Thread.currentThread().name)
        viewModel.load()

    }

    private fun loadMore() {

        viewModel.load(adapter.itemCount)

    }
}