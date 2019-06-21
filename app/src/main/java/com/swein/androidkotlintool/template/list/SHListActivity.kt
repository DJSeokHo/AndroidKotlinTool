package com.swein.androidkotlintool.template.list

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.constants.Constants
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.framework.util.screen.ScreenUtil
import com.swein.androidkotlintool.framework.util.thread.ThreadUtil
import com.swein.androidkotlintool.template.list.adapter.SHListAdapter
import com.swein.androidkotlintool.template.list.adapter.item.model.ItemDataModel
import com.swein.androidkotlintool.template.navigationbar.NavigationBarTemplate

class SHListActivity : Activity() {

    companion object {
        private const val TAG: String = "SHListActivity"
    }

    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var recyclerView: RecyclerView? = null
    private var layoutManager: LinearLayoutManager? = null
    private var shListAdapter: SHListAdapter? = null

    private var frameLayoutNavigationContainer: FrameLayout? = null
    private var frameLayoutProgress: FrameLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sh_list)

        ScreenUtil.setTitleBarColor(this, Color.parseColor(Constants.APP_BASIC_THEME_COLOR))

        findView()
        initNavigationBar()
        initList()
        updateView()
    }

    private fun findView() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        recyclerView = findViewById(R.id.recyclerView)
        frameLayoutNavigationContainer = findViewById(R.id.frameLayoutNavigationContainer)
        frameLayoutProgress = findViewById(R.id.frameLayoutProgress)
    }

    private fun initNavigationBar() {
        frameLayoutNavigationContainer?.addView(

            NavigationBarTemplate(this).setDelegate(object: NavigationBarTemplate.NavigationBarTemplateDelegate {

                override fun onLeftButtonClick() {

                }

                override fun onRightButtonClick() {

                }
            }).setTitle("List").hideRightButton().hideLeftButton().getView()
        )
    }

    private fun initList() {

        layoutManager = LinearLayoutManager(this)
        shListAdapter = SHListAdapter()

        swipeRefreshLayout?.setOnRefreshListener {

            reload()

            swipeRefreshLayout?.isRefreshing = false
        }

        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = shListAdapter

        recyclerView?.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                /*  */
//                val totalItemCount = recyclerView.layoutManager!!.itemCount
//                val lastVisibleItemPosition =  layoutManager!!.findLastVisibleItemPosition()
//
//                if (newState == 0 && totalItemCount == lastVisibleItemPosition + 1) {
//                    loadMore()
//                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = recyclerView.layoutManager!!.itemCount
                val lastVisibleItemPosition =  layoutManager!!.findLastVisibleItemPosition()

                if (totalItemCount == lastVisibleItemPosition + 2) {
                    loadMore()
                }
            }
        })
    }

    private fun updateView() {
        loadMore()
    }

    private fun showProgress() {
        frameLayoutProgress?.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        frameLayoutProgress?.visibility = View.GONE
    }

    private fun loadMore() {

        showProgress()

        ThreadUtil.startThread(Runnable {
            shListAdapter?.loadMore()

            ThreadUtil.startUIThread(1000, Runnable {
                shListAdapter?.notifyDataSetChanged()
                hideProgress()
            })
        })
    }

    private fun reload() {

        showProgress()

        ThreadUtil.startThread(Runnable {
            shListAdapter?.reload()

            ThreadUtil.startUIThread(1500, Runnable {
                shListAdapter?.notifyDataSetChanged()
                hideProgress()
            })
        })

    }


}
