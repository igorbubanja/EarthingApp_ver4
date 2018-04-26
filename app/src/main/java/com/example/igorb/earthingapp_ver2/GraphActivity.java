package com.example.igorb.earthingapp_ver2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        LineChart chart = (LineChart) findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<Entry>();
        Bundle extras = getIntent().getExtras();
        ArrayList<TestScenario> testList = new ArrayList<TestScenario>();
        if(extras != null){
            testList = (ArrayList<TestScenario>) extras.getSerializable("testScenarios");
        }

        for (TestScenario test: testList){
            if(test.resistance >=0 && test.distance >= 0 ) {
                Entry myEntry = new Entry((float) test.resistance, (float) test.distance);
                entries.add(myEntry);
            }
        }
        Collections.sort(entries, new EntryXComparator()); //entries musst be sorted by their x value for the graph to work properly

        if(entries.size()>0) {
            LineDataSet dataSet = new LineDataSet(entries, "Label");
            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
        }

        chart.invalidate();

    }
}
