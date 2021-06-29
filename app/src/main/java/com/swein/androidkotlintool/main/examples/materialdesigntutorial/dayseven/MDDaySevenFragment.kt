package com.swein.androidkotlintool.main.examples.materialdesigntutorial.dayseven

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.materialdesigntutorial.dayseven.adapter.MDDaySevenAdapter

class MDDaySevenFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() =
            MDDaySevenFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private lateinit var rootView: View

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        rootView.findViewById(R.id.swipeRefreshLayout)
    }
    private val recyclerView: RecyclerView by lazy {
        rootView.findViewById(R.id.recyclerView)
    }

    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    private lateinit var adapter: MDDaySevenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_m_d_day_seven, container, false)

        initList()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reload()
    }

    private fun initList() {

        swipeRefreshLayout.setOnRefreshListener {

            reload()

            swipeRefreshLayout.isRefreshing = false
        }

        recyclerView.layoutManager = layoutManager

        adapter = MDDaySevenAdapter()
        adapter.onLoadMore = {
            // load more
            loadMore()
        }

        recyclerView.adapter = adapter
    }

    private fun reload() {

        val list = getTempDataFromDummyServer(0, 20)

        recyclerView.post {
            adapter.reload(list)
        }
    }

    private fun loadMore() {

        val list = getTempDataFromDummyServer(adapter.itemCount, 10)

        recyclerView.post {
            adapter.loadMore(list)

        }
    }

    private fun getTempDataFromDummyServer(offset: Int, limit: Int): MutableList<String> {

        val list: MutableList<String> = mutableListOf()

        for(i in offset until (offset + limit)) {
            list.add("content index $i")
        }

        return list
    }

}