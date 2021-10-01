package com.swein.androidkotlintool.main.examples.pagingexample

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.pagingexample.adapter.GithubRepoAdapter
import com.swein.androidkotlintool.main.examples.pagingexample.adapter.GithubRepoLoadStateAdapter
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

    private val progressBar: ProgressBar by lazy {
        findViewById(R.id.progressBar)
    }
    private val textviewEmpty: TextView by lazy {
        findViewById(R.id.textviewEmpty)
    }
    private val buttonRetry: Button by lazy {
        findViewById(R.id.buttonRetry)
    }

    private val adapter = GithubRepoAdapter()

    private val viewModel: GithubRepoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging_example)

        setListener()
        initList()

    }

    private fun setListener() {
        buttonRetry.setOnClickListener {
            adapter.retry()
        }
    }

    private fun initList() {
        lifecycleScope.launch {

            whenCreated {
                viewModel.pagingDataFlow.collect { pagingData ->
                    adapter.submitData(pagingData)
                }
            }

        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = GithubRepoLoadStateAdapter {
                adapter.retry()
            },
            footer = GithubRepoLoadStateAdapter {
                adapter.retry()
            }
        )

        adapter.addLoadStateListener { loadState ->

            // show empty list
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0

            textviewEmpty.isVisible = isListEmpty
            // Only show the list if refresh succeeds.
            swipeRefreshLayout.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            buttonRetry.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }

        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            adapter.refresh()
        }
    }
}