package com.swein.androidkotlintool.main.examples.chartexample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.swein.androidkotlintool.R

class ChartExampleActivity : AppCompatActivity() {

    private val lineChart: LineChart by lazy {
        findViewById(R.id.lineChart)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart_example)

        // invalidate() : 在chart中调用会使其刷新重绘
        lineChart.invalidate()

        // notifyDataSetChanged() : 让chart知道它依赖的基础数据已经改变，并执行所有必要的重新计算（比如偏移量，legend，最大值，最小值 …）。在动态添加数据时需要用到。
        lineChart.notifyDataSetChanged()


//        val list = mutableListOf<Entry>()
//        var entry = Entry(1.0f, 1.0f)
//        list.add(entry)
//        entry = Entry(1.5f, 1.3f)
//        list.add(entry)
//        entry = Entry(2f, 1.1f)
//        list.add(entry)
//
//        val lineDataSet = LineDataSet(list, "test")
//        lineDataSet.lineWidth = 2.5f
//        lineDataSet.circleRadius = 4.5f
//        lineDataSet.highLightColor = Color.RED
//        lineDataSet.setDrawValues(true) // 是否在点上绘制Value
//        lineDataSet.valueTextColor = Color.GREEN
//        lineDataSet.valueTextSize = 12f



        val companyAValueList = mutableListOf<Entry>()
        val companyBValueList = mutableListOf<Entry>()

        companyAValueList.add(Entry(100f, 0f))
        companyAValueList.add(Entry(50f, 1f))
        companyAValueList.add(Entry(70f, 2f))
        companyAValueList.add(Entry(120f, 3f))

        companyBValueList.add(Entry(70f, 0f))
        companyBValueList.add(Entry(60f, 1f))
        companyBValueList.add(Entry(90f, 2f))
        companyBValueList.add(Entry(100f, 3f))

        val companyALineDataSet = LineDataSet(companyAValueList, "Company A")
        companyALineDataSet.axisDependency = YAxis.AxisDependency.LEFT
        companyALineDataSet.lineWidth = 2.5f
        companyALineDataSet.circleRadius = 4.5f
//        companyALineDataSet.highLightColor = Color.RED
        companyALineDataSet.setDrawValues(true) // 是否在点上绘制Value
//        companyALineDataSet.valueTextColor = Color.GREEN
        companyALineDataSet.valueTextSize = 12f

        val companyBLineDataSet = LineDataSet(companyBValueList, "Company B")
        companyALineDataSet.axisDependency = YAxis.AxisDependency.LEFT
        companyBLineDataSet.lineWidth = 2.5f
        companyBLineDataSet.circleRadius = 4.5f
//        companyBLineDataSet.highLightColor = Color.RED
        companyBLineDataSet.setDrawValues(true) // 是否在点上绘制Value
//        companyBLineDataSet.valueTextColor = Color.GREEN
        companyBLineDataSet.valueTextSize = 12f

        val companyLineDataSetList = mutableListOf<ILineDataSet>()
        companyLineDataSetList.add(companyALineDataSet)
        companyLineDataSetList.add(companyBLineDataSet)

        val lineData = LineData(companyLineDataSetList)
        lineChart.data = lineData
        lineChart.invalidate()

        val xValueList = mutableListOf<String>()
        xValueList.add("1.Q")
        xValueList.add("2.Q")
        xValueList.add("3.Q")
        xValueList.add("4.Q")

        val formatter = object: ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return xValueList[value.toInt()]
            }
        }

        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)

        xAxis.setDrawAxisLine(true)

        xAxis.granularity = 1f
        xAxis.valueFormatter = formatter
    }
}