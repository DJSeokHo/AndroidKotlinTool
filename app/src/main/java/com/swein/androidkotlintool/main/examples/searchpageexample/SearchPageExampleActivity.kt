package com.swein.androidkotlintool.main.examples.searchpageexample

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import java.lang.ref.WeakReference

class SearchPageExampleActivity : AppCompatActivity() {

    private val editText: EditText by lazy {
        findViewById(R.id.editText)
    }

    private val textViewClear: TextView by lazy {
        findViewById(R.id.textViewClear)
    }

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    private val progress: FrameLayout by lazy {
        findViewById(R.id.progress)
    }

    private lateinit var adapter: SearchPageAdapter

    private var searchKeyword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_page_example)

        DummySearchServer.initData()

        editText.addTextChangedListener {
            searchKeyword = it.toString().trim()
        }
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                dismissKeyboard(this)

                search()

                return@setOnEditorActionListener true
            }
            false
        }

        // if edit text is empty, then reload the list

        // reset
        textViewClear.setOnClickListener {

            dismissKeyboard(this)
            editText.setText("")
            searchKeyword = ""
            reload()
        }

        // init list
        adapter = SearchPageAdapter {

            if (searchKeyword != "") {
                // This example will disable load more when searching
                return@SearchPageAdapter
            }

            loadMore()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        // reload
        reload()
    }

    private fun dismissKeyboard(activity: Activity?) {
        val inputMethodManager: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus

        if(view == null) {
            view = View(activity)
        }
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun search() {

        showProgress()

        Thread {

            // io thread
            val list = DummySearchServer.searchAll(searchKeyword)

            recyclerView.post {

                // UI thread
                hideProgress()
                adapter.reload(list)
            }

        }.start()
    }

    private fun reload() {

        showProgress()

        Thread {

            // io thread
            val list = DummySearchServer.fetch(0, 10)

            recyclerView.post {

                // UI thread
                hideProgress()
                adapter.reload(list)
            }

        }.start()
    }

    private fun loadMore() {

        showProgress()

        Thread {

            // io thread
            val list = DummySearchServer.fetch(adapter.itemCount, 5)

            recyclerView.post {

                // UI thread
                hideProgress()
                adapter.loadMore(list)
            }

        }.start()
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }
}

private class SearchPageAdapter(
    private val onLoadMore: () -> Unit,
) : RecyclerView.Adapter<SearchPageItemViewHolder>() {

    private val list = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPageItemViewHolder {
        return SearchPageItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_search_page_item, parent, false))
    }

    override fun onBindViewHolder(holder: SearchPageItemViewHolder, position: Int) {

        holder.content = list[position]
        holder.updateView()

        if (position == list.size - 1) {
            onLoadMore()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reload(list: List<String>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun loadMore(list: List<String>) {
        this.list.addAll(list)
        notifyItemRangeChanged(this.list.size - list.size + 1, list.size)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

private class SearchPageItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val view: WeakReference<View> = WeakReference(itemView)
    private var textView: TextView? = null

    var content = ""

    init {

        view.get()?.let {
            textView = it.findViewById(R.id.textView)
        }
    }

    fun updateView() {
        textView?.text = content
    }
}

private object DummySearchServer {

    // This is just a simple dummy server example for this video
    // You server developer should finish this part and offer some rest apis for you.

    private val list = mutableListOf<String>()


    fun initData() {

        list.clear()

        for (i in 0 until 10) {
            list.add("content $i")
        }

        list.add("aaa")
        list.add("bb")
        list.add("CCC")
        list.add("aaabbbCCCDDD")
        list.add("123AAA456")

        for (i in 10 until 20) {
            list.add("content $i")
        }

        list.add("Coding with cat")
    }

    // fetch API for reloading and loading more
    fun fetch(offset: Int, limit: Int): List<String> {

        Thread.sleep(500)

        val tempList = mutableListOf<String>()

        for (i in offset until offset + limit) {

            if (i == list.size) {
                return tempList
            }

            tempList.add(list[i])
        }

        return tempList
    }

    // search API
    fun searchAll(searchKeyword: String): List<String> {

        val tempList = mutableListOf<String>()

        for (i in 0 until list.size) {

            if (i == list.size) {
                return tempList
            }

            val content = list[i]

            if (content.lowercase().contains(searchKeyword.lowercase())) {
                tempList.add(content)
            }
        }

        return tempList
    }
}