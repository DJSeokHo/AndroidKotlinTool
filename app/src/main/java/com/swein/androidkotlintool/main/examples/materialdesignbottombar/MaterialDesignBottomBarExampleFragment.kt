package com.swein.androidkotlintool.main.examples.materialdesignbottombar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.thread.ThreadUtility
import com.swein.androidkotlintool.main.examples.coordinatorlayoutexample.adapter.CoordinatorLayoutExampleAdapter

class MaterialDesignBottomBarExampleFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance(content: String) =
            MaterialDesignBottomBarExampleFragment().apply {
                arguments = Bundle().apply {
                    putString("content", content)
                }
            }
    }

    private var content = ""

    private lateinit var rootView: View
    private val textView: TextView by lazy {
        rootView.findViewById(R.id.textView)
    }

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        rootView.findViewById(R.id.swipeRefreshLayout)
    }
    private val recyclerView: RecyclerView by lazy {
        rootView.findViewById(R.id.recyclerView)
    }
    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }
    private val adapter: CoordinatorLayoutExampleAdapter by lazy {
        CoordinatorLayoutExampleAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            content = it.getString("content", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(
            R.layout.fragment_material_design_bottom_bar_example,
            container,
            false
        )
        initList()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView.text = content
        reload()
    }

    private fun initList() {

        swipeRefreshLayout.setOnRefreshListener {

            reload()

            swipeRefreshLayout.isRefreshing = false
        }

        adapter.onLoadMore = {

            ThreadUtility.startUIThread(0) {
                loadMore()
            }
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun reload() {

        adapter.reload(createTempData(0, 10))
    }

    private fun loadMore() {

        adapter.loadMore(createTempData(adapter.itemCount, 10))
    }

    fun backToTop() {
        recyclerView.smoothScrollToPosition(0)
    }

    private fun createTempData(offset: Int, limit: Int): MutableList<String> {
        val list: MutableList<String> = mutableListOf()

        for(i in offset..(offset + limit)) {
            list.add("title $i")
        }

        return list
    }
}