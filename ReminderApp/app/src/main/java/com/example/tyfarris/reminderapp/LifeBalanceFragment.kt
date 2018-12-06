package com.example.tyfarris.reminderapp

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_life_balance.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*


class LifeBalanceFragment : Fragment() {

//    var temp = listOf(Pair("Walk dog", 1.0f), Pair("Clean room", 2.0f),
//            Pair("Make food", 1.0f), Pair("Smoothies", 3.0f),
//            Pair("Dinner with friends", 3.0f), Pair("Yoga", 1.0f))
//
//    val labels = listOf("Health", "Work", "Social")

    private lateinit var model: MyModelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity.run {
            ViewModelProviders.of(activity!!).get(MyModelView::class.java)
        }

        //change the title of the bar
        activity?.title = "Life Balance"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_life_balance, container, false)
        var chart = view.findViewById<PieChart>(R.id.pie_chart)

        //set the info button to invisible
        activity?.toolbar_info_logo?.visibility = View.INVISIBLE

        setUpPieChart(chart)
        return view
    }

    //have a case statement to categorize and create a social/health/work pie entry
    fun setUpPieChart(chart: PieChart) {
        var pieEntries = mutableListOf<PieEntry>()

        if(model.selectedListPosition != -1) {
            for (data in model.lstDirectory[model.selectedListPosition].reminderList) {
                when (data.category) {
                    1.0f -> {
                        pieEntries.add(PieEntry(1.0f, "Social"))
                    }
                    2.0f -> {
                        pieEntries.add(PieEntry(1.0f, "Work"))
                    }
                    3.0f -> {
                        pieEntries.add(PieEntry(1.0f, "Health"))
                    }
                }
            }
        }

//        pieEntries.add(PieEntry(temp[0].second, "Health"))
//        pieEntries.add(PieEntry(temp[1].second, "Work"))
//        pieEntries.add(PieEntry(temp[3].second, "Social"))

        var dataSet = PieDataSet(pieEntries, "Life Balance")
        dataSet.setColors(ColorTemplate.createColors(ColorTemplate.COLORFUL_COLORS))

        //create the pie chart
        var data = PieData(dataSet)
        chart.data = data
        chart.animateY(1000)
        chart.invalidate()

        //to set the percent values
        data.setValueFormatter(PercentFormatter())
        chart.setUsePercentValues(true)

        //set the chart title
        if (model.selectedListPosition != -1)
            chart.centerText = model.lstDirectory[model.selectedListPosition].listName
        chart.setCenterTextSize(20f)

        //set the data text size
        data.setValueTextSize(20f)
        data.setValueTextColor(Color.WHITE)

        chart.setEntryLabelTextSize(20f)

        //set center hole size
        chart.isDrawHoleEnabled = true
        chart.transparentCircleRadius = 40f
        chart.setHoleColor(Color.WHITE)

        //legend
        chart.legend.position = Legend.LegendPosition.BELOW_CHART_CENTER
        chart.legend.textSize = 15f

        //get rid off the description which is in the bottom right corner that defaults to Life Balance
        chart.description.isEnabled = false
    }
}

//TODO: CHECK OUT WHEN NULL :O