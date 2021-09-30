package com.swein.androidkotlintool.main.examples.pagingexample

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.pagingexample.adapter.GithubRepoAdapter
import com.swein.androidkotlintool.main.examples.pagingexample.viewmodel.GithubRepoViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PagingExampleActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PagingExampleActivity"
    }

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        findViewById(R.id.swipeRefreshLayout)
    }

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    private val progress: FrameLayout by lazy {
        findViewById(R.id.progress)
    }

    private val adapter = GithubRepoAdapter()

    private val viewModel: GithubRepoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging_example)

        lifecycleScope.launch {

            whenCreated {
                viewModel.pagingDataFlow.collect { pagingData ->
                    adapter.submitData(pagingData)
                }
            }

        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        adapter.addLoadStateListener {

            when (it.refresh) {

                is LoadState.NotLoading -> {
                    ILog.debug(TAG, "refresh NotLoading")
                    hideProgress()
                }

                is LoadState.Loading -> {
                    ILog.debug(TAG, "refresh Loading")
                    showProgress()
                }

                is LoadState.Error -> {
                    ILog.debug(TAG, "refresh ERROR")
                    val stateError = it.refresh as LoadState.Error
                    hideProgress()
                    Toast.makeText(this, "${stateError.error.message}", Toast.LENGTH_SHORT).show()
                }
            }

            when (it.append) {

                is LoadState.NotLoading -> {
                    ILog.debug(TAG, "append NotLoading")
                }

                is LoadState.Loading -> {
                    ILog.debug(TAG, "append Loading")
                }

                is LoadState.Error -> {
                    ILog.debug(TAG, "append ERROR")
                    val stateError = it.append as LoadState.Error
                    Toast.makeText(this, "${stateError.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            adapter.refresh()
        }
    }


    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }

}