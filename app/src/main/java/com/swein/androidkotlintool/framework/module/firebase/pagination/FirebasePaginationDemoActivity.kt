package com.swein.androidkotlintool.framework.module.firebase.pagination

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.firestore.DocumentSnapshot
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.firebase.cloudfirestore.CloudFireStoreWrapper
import com.swein.androidkotlintool.framework.module.firebase.pagination.adapter.FirebasePaginationAdapter
import com.swein.androidkotlintool.framework.module.firebase.pagination.model.FirebasePaginationItemModel
import com.swein.androidkotlintool.framework.module.firebase.pagination.viewmodel.FirebasePaginationViewModel
import com.swein.androidkotlintool.framework.module.firebase.pagination.viewmodel.FirebasePaginationViewModelState
import com.swein.androidkotlintool.framework.utility.date.DateUtility
import com.swein.androidkotlintool.framework.utility.debug.ILog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FirebasePaginationDemoActivity : AppCompatActivity() {

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        findViewById(R.id.swipeRefreshLayout)
    }

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    private lateinit var firebasePaginationAdapter: FirebasePaginationAdapter

    private val layoutManager = LinearLayoutManager(this)

    private val progress: FrameLayout by lazy {
        findViewById(R.id.progress)
    }

    private var offset: DocumentSnapshot? = null
    private val viewModel: FirebasePaginationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_pagination_demo)

        initFlow()
        initList()
        reload()

//        createDummyData()
    }

    private fun initFlow() {
        lifecycleScope.launch(Dispatchers.Main) {

            whenCreated {

                viewModel.viewModelState.collect {

                    when (it) {

                        is FirebasePaginationViewModelState.ReloadSuccessfully -> {
                            hideProgress()

                            this@FirebasePaginationDemoActivity.offset = it.offset
                            ILog.debug("??", "offset is ${it.offset.data}")
                            firebasePaginationAdapter.reload(it.list)
                        }

                        is FirebasePaginationViewModelState.LoadMoreSuccessfully -> {
                            hideProgress()

                            this@FirebasePaginationDemoActivity.offset = it.offset
                            ILog.debug("??", "offset is ${it.offset.data}")
                            firebasePaginationAdapter.loadMore(it.list)
                        }

                        is FirebasePaginationViewModelState.Error -> {
                            hideProgress()
                            Toast.makeText(this@FirebasePaginationDemoActivity, it.message, Toast.LENGTH_SHORT).show()
                        }

                        is FirebasePaginationViewModelState.Loading -> {
                            showProgress()
                        }

                        is FirebasePaginationViewModelState.Empty -> {
                            hideProgress()
                            Toast.makeText(this@FirebasePaginationDemoActivity, "no more data", Toast.LENGTH_SHORT).show()
                        }

                        else -> Unit
                    }
                }

            }

        }
    }

    private fun initList() {

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            reload()
        }

        firebasePaginationAdapter = FirebasePaginationAdapter {
            loadMore()
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = firebasePaginationAdapter
    }

    private fun reload() {
        viewModel.reload(orderBy = FirebasePaginationItemModel.CREATE_DATE_KEY, limit = 20)
    }

    private fun loadMore() {
        viewModel.loadMore(orderBy = FirebasePaginationItemModel.CREATE_DATE_KEY, offset = offset, limit = 20)
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }


    private fun createDummyData() {

        lifecycleScope.launch(Dispatchers.IO) {

            var firebasePaginationItemModel: FirebasePaginationItemModel
            for (i in 0 until 53) {

                ILog.debug("???", "create dummy data $i")
                firebasePaginationItemModel = FirebasePaginationItemModel(
                    title = "title $i",
                    message = "message $i",
                    createDate = DateUtility.getCurrentDateTimeString()
                )

                firebasePaginationItemModel.tag = if (i % 3 == 0) {
                    "aaa"
                }
                else {
                    "bbb"
                }

                CloudFireStoreWrapper.replace("TEST_PATH", "document$i", firebasePaginationItemModel.to())
                
                delay(1500)
            }

        }


    }
}