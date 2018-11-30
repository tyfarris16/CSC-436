package com.example.tyfarris.reminderapp

import android.arch.lifecycle.ViewModelProviders
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
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.*


class LifeBalanceFragment : Fragment() {

    var temp = listOf(Pair("Walk dog", 1.0f), Pair("Clean room", 2.0f),
            Pair("Make food", 1.0f), Pair("Smoothies", 3.0f),
            Pair("Dinner with friends", 3.0f), Pair("Yoga", 1.0f))

    val labels = listOf("Health", "Work", "Social")

    private lateinit var model: MyModelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity.run {
            ViewModelProviders.of(activity!!).get(MyModelView::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_life_balance, container, false)
        var chart = view.findViewById<PieChart>(R.id.pie_chart)

        setUpPieChart(chart)
        return view
    }

    //have a case statement to categorize and create a social/health/work pie entry
    fun setUpPieChart(chart: PieChart) {
        var pieEntries = mutableListOf<PieEntry>()

//        for (data in temp){
////            when (data.second) {
////                1.0f -> {
////                    pieEntries.add(PieEntry(data.second, "Health"))
////                }
////                2.0f -> {
////                    pieEntries.add(PieEntry(data.second, "Work"))
////                }
////                3.0f -> {
////                    pieEntries.add(PieEntry(data.second, "Health"))
////                }
////            }
//        }

        pieEntries.add(PieEntry(temp[0].second, "Health"))
        pieEntries.add(PieEntry(temp[1].second, "Work"))
        pieEntries.add(PieEntry(temp[3].second, "Social"))


        var dataSet = PieDataSet(pieEntries, "Life Balance")
        dataSet.setColors(ColorTemplate.createColors(ColorTemplate.COLORFUL_COLORS))

        var data = PieData(dataSet)

        chart.setData(data)
        chart.animateY(1000)
        chart.centerText = "Daily"
        chart.setUsePercentValues(true)
        chart.invalidate()
    }
}