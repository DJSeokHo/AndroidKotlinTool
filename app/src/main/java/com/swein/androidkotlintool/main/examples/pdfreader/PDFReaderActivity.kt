package com.swein.androidkotlintool.main.examples.pdfreader

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.main.examples.pdfreader.adapter.PDFReaderAdapter
import com.swein.androidkotlintool.main.examples.pdfreader.viewmodel.PDFReaderViewModel
import com.swein.androidkotlintool.main.examples.pdfreader.viewmodel.PDFReaderViewModelState
import com.swein.androidkotlintool.main.examples.permissionexample.PermissionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PDFReaderActivity : AppCompatActivity() {

    private val permissionManager = PermissionManager(this)

    private val progress: LinearLayout by lazy {
        findViewById(R.id.progress)
    }

    private val textViewProgress: TextView by lazy {
        findViewById(R.id.textViewProgress)
    }

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    private lateinit var adapter: PDFReaderAdapter

    private val buttonLeft: Button by lazy {
        findViewById(R.id.buttonLeft)
    }

    private val buttonRight: Button by lazy {
        findViewById(R.id.buttonRight)
    }

    private val textViewPage: TextView by lazy {
        findViewById(R.id.textViewPage)
    }


    private val viewModel by viewModels<PDFReaderViewModel>()


    private val totalPdfBitmapList = mutableListOf<Bitmap>()

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfreader)

        initFlow()
        initList()
        setListener()

        permissionManager.requestPermission(
            "Permission",
            "permissions are necessary",
            "setting",
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        ) {

            // https://file-examples.com/index.php/sample-documents-download/sample-pdf-download/
//            viewModel.pdf(this, "https://file-examples.com/storage/fe8d415a4e625f28ea2bfd7/2017/10/file-example_PDF_1MB.pdf")
            viewModel.pdf(this, "https://file-examples.com/storage/fe8d415a4e625f28ea2bfd7/2017/10/file-example_PDF_500_kB.pdf")

        }
    }

    private fun initFlow() {
        lifecycleScope.launch(Dispatchers.Main) {

            whenCreated {

                viewModel.pdfReaderViewModelState.collect {

                    when (it) {

                        is PDFReaderViewModelState.OnPDFFile -> {
                            ILog.debug("???", "file ${it.file.absoluteFile} ${it.file.length()}")
                            hideProgress()

                            viewModel.bitmaps(it.file, getScreenWidth(this@PDFReaderActivity))
                        }

                        is PDFReaderViewModelState.OnBitmaps -> {
                            ILog.debug("???", "bitmaps ${it.list.size}")
                            hideProgress()

                            totalPdfBitmapList.clear()
                            totalPdfBitmapList.addAll(it.list)

                            @SuppressLint("SetTextI18n")
                            textViewPage.text = "${currentIndex + 1}/${totalPdfBitmapList.size}"

                            reload()
                        }

                        is PDFReaderViewModelState.Error -> {
                            Toast.makeText(this@PDFReaderActivity, it.message, Toast.LENGTH_SHORT).show()
                            hideProgress()
                        }

                        is PDFReaderViewModelState.Empty -> {
                            Toast.makeText(this@PDFReaderActivity, "empty", Toast.LENGTH_SHORT).show()
                            hideProgress()
                        }

                        is PDFReaderViewModelState.Loading -> {
                            showProgress()
                        }

                        is PDFReaderViewModelState.Progress -> {

                            textViewProgress.post {
                                textViewProgress.text = if (it.progress != 0) {
                                    "${it.progress}%"
                                }
                                else {
                                    ""
                                }
                            }

                        }

                        is PDFReaderViewModelState.None -> Unit
                    }
                }

            }

        }
    }

    private fun initList() {

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapter = PDFReaderAdapter {
            loadMore()
        }

        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

                    currentIndex = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    @SuppressLint("SetTextI18n")
                    textViewPage.text = "${currentIndex + 1}/${totalPdfBitmapList.size}"
                }
            }
        })

        PagerSnapHelper().attachToRecyclerView(recyclerView)
    }


    private fun setListener() {

        buttonLeft.setOnClickListener {

            currentIndex -= 1
            if (currentIndex < 0) {
                currentIndex = 0
            }

            recyclerView.smoothScrollToPosition(currentIndex)

            @SuppressLint("SetTextI18n")
            textViewPage.text = "${currentIndex + 1}/${totalPdfBitmapList.size}"
        }

        buttonRight.setOnClickListener {

            currentIndex += 1
            if (currentIndex > totalPdfBitmapList.size - 1) {
                currentIndex = totalPdfBitmapList.size - 1
            }

            recyclerView.smoothScrollToPosition(currentIndex)

            @SuppressLint("SetTextI18n")
            textViewPage.text = "${currentIndex + 1}/${totalPdfBitmapList.size}"
        }
    }

    private fun reload() {

        recyclerView.post {

            adapter.reload(fetchData(0, 10))
        }
    }

    private fun loadMore() {

        recyclerView.post {

            adapter.loadMore(fetchData(adapter.itemCount, 5))
        }

    }

    private fun fetchData(offset: Int, limit: Int): List<Bitmap> {

        val list = mutableListOf<Bitmap>()

        for (i in offset until offset + limit) {

            if (i > totalPdfBitmapList.size - 1) {
                break
            }

            list.add(totalPdfBitmapList[i])
        }

        return list
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }

    private fun getScreenWidth(context: Context): Int {
        val outMetrics = DisplayMetrics()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val display = context.display
            display?.getRealMetrics(outMetrics)
        }
        else {
            @Suppress("DEPRECATION")
            val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(outMetrics)
        }

        return outMetrics.widthPixels
    }
}