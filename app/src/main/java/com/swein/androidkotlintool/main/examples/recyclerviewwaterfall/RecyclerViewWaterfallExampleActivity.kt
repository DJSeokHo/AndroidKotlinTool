package com.swein.androidkotlintool.main.examples.recyclerviewwaterfall

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.recyclerviewwaterfall.adapter.WaterfallAdapter

class RecyclerViewWaterfallExampleActivity : AppCompatActivity() {

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        findViewById(R.id.swipeRefreshLayout)
    }

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    private val adapter = WaterfallAdapter()
    private val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_waterfall_example)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            reload()
        }

        adapter.onLoadMore = {
            loadMore()
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter


        reload()
    }

    private fun reload() {
        recyclerView.post {
            adapter.reload(fetchDummyData(0, 25))
        }
    }

    private fun loadMore() {
        recyclerView.post {
            adapter.loadMore(fetchDummyData(adapter.itemCount, 20))
        }
    }

    private fun fetchDummyData(offset: Int, limit: Int): List<WaterfallItemModel> {

        val list = mutableListOf<WaterfallItemModel>()

        for (i in offset until offset + limit) {

            when ((1..10).random()) {
                1 -> {
                    list.add(WaterfallItemModel("ELIZABETH ST", "https://64.media.tumblr.com/e7e05d3dd68faecd7ce3762cb5fa83ee/tumblr_pxz262Xbz21sfie3io1_1280.jpg"))
                }
                2 -> {
                    list.add(WaterfallItemModel("THE ZIG-ZAG", "https://64.media.tumblr.com/6fd84be76444e90b1639a7865f6b0009/tumblr_pxz25pnRfG1sfie3io1_1280.jpg"))
                }
                3 -> {
                    list.add(WaterfallItemModel("BRIDGE STREET AND THE EXCHANGE, SYDNEY", "https://64.media.tumblr.com/a1bed6a1843caeac48e15644845973f1/tumblr_pxz25olnlI1sfie3io1_1280.jpg"))
                }
                4 -> {
                    list.add(WaterfallItemModel("FARM COVE FROM MACQUARIE STREET", "https://64.media.tumblr.com/73452e0ca302cf9179bfd500ff45f003/tumblr_pxz22yvLWm1sfie3io1_1280.jpg"))
                }
                5 -> {
                    list.add(WaterfallItemModel("CELL BLOCK - OTTAWA POLICE STATION #1", "https://64.media.tumblr.com/97b595a78f0aeca82263d9f4715c5ed4/tumblr_pxz22xAvpd1sfie3io1_1280.jpg"))
                }
                6 -> {
                    list.add(WaterfallItemModel("SHOWING MOAT SURROUNDING POLICE STATION, LOOKING NORTH TOWARDS C.N.R. HOTEL [OTTAWA, ONTARIO]", "https://64.media.tumblr.com/e39eb98a1719e2abd17927e6d5251025/tumblr_pxz22wvRAj1sfie3io1_1280.jpg"))
                }
                7 -> {
                    list.add(WaterfallItemModel("ADMINISTRATIVE OFFICES AND CHIEF INSPECTOR’S ACCOMODATION [OTTAWA POLICE DEPARTMENT]", "https://64.media.tumblr.com/b42a00e5ef4656e7d95c40fee887217b/tumblr_pxz22uRVys1sfie3io1_1280.jpg"))
                }
                8 -> {
                    list.add(WaterfallItemModel("AERIAL VIEW SHOWING AND EMPHASIZING MOAT [OTTAWA POLICE DEPARTMENT]", "https://64.media.tumblr.com/bf90ecec728f4f167a0ffcec172315b7/tumblr_pxz22sRcO71sfie3io1_1280.jpg"))
                }
                9 -> {
                    list.add(WaterfallItemModel("SKYLAB LAUNCHES – MAY 14, 1973", "https://64.media.tumblr.com/ccdf1c8b523112b0b76314e5aecfa755/tumblr_ps7ycpodfV1sfie3io1_1280.jpg"))
                }
                10 -> {
                    list.add(WaterfallItemModel("EXPEDITION 31 CREW PREPARES FOR LAUNCH", "https://64.media.tumblr.com/0b5469eef13ccfec6823793187117b16/tumblr_ps7ycl6ucn1sfie3io1_1280.jpg"))
                }
            }
        }

        return list
    }
}

data class WaterfallItemModel(
    val title: String,
    val imageUrl: String
)