package com.example.baobang.threebird.utils;

import android.graphics.Color;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.io.Serializable;
import java.util.ArrayList;


public class LineChart implements Serializable {
    private com.github.mikephil.charting.charts.LineChart mChart;
    private ArrayList<Entry> yValues;
    private ArrayList<String> xValues;
    private String title;
    public LineChart(String title, com.github.mikephil.charting.charts.LineChart mChart,
                     ArrayList<Entry> yValues,
                     ArrayList<String> xValues){
        this.mChart = mChart;
        this.xValues = xValues;
        this.yValues = yValues;
        this.title = title;
    }

    public void drawLineChart(){
        // add data
        setData();
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        mChart.getLegend().setForm(Legend.LegendForm.LINE);

        // no description text
        mChart.setNoDataTextDescription("You need to provide data for the chart.");
        mChart.setDescription("");
        // enable touch gestures
        mChart.setTouchEnabled(true);
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawBorders(false);

        LimitLine lower_limit = new LimitLine(getMinValue(yValues), "Doanh thu thấp nhất");
        lower_limit.setLineWidth(2f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLimitLinesBehindData(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(lower_limit);
        leftAxis.setAxisMinValue(getMinValue(yValues));
        leftAxis.setDrawLimitLinesBehindData(false);

        mChart.getAxisRight().setEnabled(false);

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        mChart.invalidate();
    }
    private void setData() {
        LineDataSet set1;
        // create a dataset and give it a type
        set1 = new LineDataSet(yValues, title);
        set1.setColor(Color.BLUE);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(2f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the datasets
        // create a data object with the datasets
        LineData data = new LineData(xValues, dataSets);
        // set data
        mChart.setData(data);

    }

    private float getMinValue(ArrayList<Entry> entries){
        if(entries.size() == 0) return 0f;
        float min = entries.get(0).getVal();
        for(Entry e : entries){
            if(e.getVal() < min)
                min = e.getVal();
        }
        return min;
    }
}
