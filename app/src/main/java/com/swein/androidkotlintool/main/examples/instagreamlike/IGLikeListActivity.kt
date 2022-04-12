package com.swein.androidkotlintool.main.examples.instagreamlike

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.main.examples.instagreamlike.adapter.IGLikeAdapter
import com.swein.androidkotlintool.main.examples.instagreamlike.ig.IGLayoutManager
import com.swein.androidkotlintool.main.examples.instagreamlike.ig.SpanSize

class IGLikeListActivity : AppCompatActivity() {

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        findViewById(R.id.swipeRefreshLayout)
    }

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    private val adapter = IGLikeAdapter()

    private var bigItemFlag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iglike_list)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            reload()
        }

        adapter.onLoadMore = {
            loadMore()
        }

        val layoutManager = IGLayoutManager(IGLayoutManager.Orientation.VERTICAL, 3)

        layoutManager.spanSizeLookup = IGLayoutManager.SpanSizeLookup { position ->

            // 0  1  2
            // s  s  b
            // 3  4  5  6  7  8  9  10 11
            // s  s  s  s  s  s  s  s  b
            // 12 13 14 15 16 17 18 19 20 21 22 23 24
            // s  s  s  s  s  s  s  s  s  s  s  s  b
            // 25 26 27 28 29 30 31 32 33
            // s  s  s  s  s  s  s  s  b
            // 34 35 36 37 38 39 40 41 42 43 44 45 46
            // s  s  s  s  s  s  s  s  s  s  s  s  b
            // 47 48 49 50 51 52 53 54 55
            // s  s  s  s  s  s  s  s  b
            // 56 57 58 59 60 61 62 63 64 65 66 67 68
            // s  s  s  s  s  s  s  s  s  s  s  s  b
            // 69 70 71 72 73 74 75 76 77
            // s  s  s  s  s  s  s  s  b
            // 78 79 80 81 82 83 84 85 86 87 88 89 90
            // s  s  s  s  s  s  s  s  s  s  s  s  b
            // 91 92 93 94 95 96 97 98 99
            // s  s  s  s  s  s  s  s  b
            // 100 101 102 103 104 105 106 107 108 109 110 111 112
            // s   s   s   s   s   s   s   s   s   s   s   s   b
            // 113 114 115 116 117 118 119 120 121
            // s   s   s   s   s   s   s   s   b

            if (position < 2) {
//                ILog.debug("???", "$position is small")
                bigItemFlag = 0
                SpanSize(1, 1)
            }
            else if (position == 2) {
//                ILog.debug("???", "$position is big")
                bigItemFlag = 11
                SpanSize(1, 2)
            }
            else if (position % 11 == 0 && position == bigItemFlag) {
                bigItemFlag += 13
//                ILog.debug("???", "$position is big, $position mod 11 is ${position % 11}")
                SpanSize(1, 2)
            }
            else if (position % 11 == 2 && position == bigItemFlag) {
                bigItemFlag += 9
//                ILog.debug("???", "$position is big, $position mod 11 is ${position % 11}")
                SpanSize(1, 2)
            }
            else {
//                ILog.debug("???", "$position is small")
                SpanSize(1, 1)
            }
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

    private fun fetchDummyData(offset: Int, limit: Int): List<IGItemModel> {

        val list = mutableListOf<IGItemModel>()

        for (i in offset until offset + limit) {

            when ((1..10).random()) {
                1 -> {
                    list.add(
                        IGItemModel(
                            "ELIZABETH ST",
                            "https://64.media.tumblr.com/e7e05d3dd68faecd7ce3762cb5fa83ee/tumblr_pxz262Xbz21sfie3io1_1280.jpg"
                        )
                    )
                }
                2 -> {
                    list.add(
                        IGItemModel(
                            "THE ZIG-ZAG",
                            "https://64.media.tumblr.com/6fd84be76444e90b1639a7865f6b0009/tumblr_pxz25pnRfG1sfie3io1_1280.jpg"
                        )
                    )
                }
                3 -> {
                    list.add(
                        IGItemModel(
                            "BRIDGE STREET AND THE EXCHANGE, SYDNEY",
                            "https://64.media.tumblr.com/a1bed6a1843caeac48e15644845973f1/tumblr_pxz25olnlI1sfie3io1_1280.jpg"
                        )
                    )
                }
                4 -> {
                    list.add(
                        IGItemModel(
                            "FARM COVE FROM MACQUARIE STREET",
                            "https://64.media.tumblr.com/73452e0ca302cf9179bfd500ff45f003/tumblr_pxz22yvLWm1sfie3io1_1280.jpg"
                        )
                    )
                }
                5 -> {
                    list.add(
                        IGItemModel(
                            "CELL BLOCK - OTTAWA POLICE STATION #1",
                            "https://64.media.tumblr.com/97b595a78f0aeca82263d9f4715c5ed4/tumblr_pxz22xAvpd1sfie3io1_1280.jpg"
                        )
                    )
                }
                6 -> {
                    list.add(
                        IGItemModel(
                            "SHOWING MOAT SURROUNDING POLICE STATION, LOOKING NORTH TOWARDS C.N.R. HOTEL [OTTAWA, ONTARIO]",
                            "https://64.media.tumblr.com/e39eb98a1719e2abd17927e6d5251025/tumblr_pxz22wvRAj1sfie3io1_1280.jpg"
                        )
                    )
                }
                7 -> {
                    list.add(
                        IGItemModel(
                            "ADMINISTRATIVE OFFICES AND CHIEF INSPECTOR’S ACCOMODATION [OTTAWA POLICE DEPARTMENT]",
                            "https://64.media.tumblr.com/b42a00e5ef4656e7d95c40fee887217b/tumblr_pxz22uRVys1sfie3io1_1280.jpg"
                        )
                    )
                }
                8 -> {
                    list.add(
                        IGItemModel(
                            "AERIAL VIEW SHOWING AND EMPHASIZING MOAT [OTTAWA POLICE DEPARTMENT]",
                            "https://64.media.tumblr.com/bf90ecec728f4f167a0ffcec172315b7/tumblr_pxz22sRcO71sfie3io1_1280.jpg"
                        )
                    )
                }
                9 -> {
                    list.add(
                        IGItemModel(
                            "SKYLAB LAUNCHES – MAY 14, 1973",
                            "https://64.media.tumblr.com/ccdf1c8b523112b0b76314e5aecfa755/tumblr_ps7ycpodfV1sfie3io1_1280.jpg"
                        )
                    )
                }
                10 -> {
                    list.add(
                        IGItemModel(
                            "EXPEDITION 31 CREW PREPARES FOR LAUNCH",
                            "https://64.media.tumblr.com/0b5469eef13ccfec6823793187117b16/tumblr_ps7ycl6ucn1sfie3io1_1280.jpg"
                        )
                    )
                }
            }
        }

        return list
    }

}

data class IGItemModel(
    val title: String,
    val imageUrl: String
)