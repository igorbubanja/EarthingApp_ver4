//version 4
package com.example.igorb.earthingapp_ver2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<TestScenario> testList = new ArrayList<>();
    int nextId = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Dynamically setting up the UI
        ScrollView linearLayout = (ScrollView) findViewById(R.id.linearLayout1);
        TableLayout inputTable = tableLayout(1);
        inputTable.setId(R.id.mainInputTable); //mainInputTable is defined as an id in ids.xml in the res folder
        linearLayout.addView(inputTable);
    }

    public void showGraph(View view){
        Intent showGraph = new Intent(this, GraphActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("testScenarios", testList);
        showGraph.putExtras(bundle);
        startActivity(showGraph);
    }

    public void calcLoop(View view){
        //calculate for all inputs
        for(TestScenario test: testList){
            test.calculate();
        }
    }

    private TableLayout tableLayout(int count) {
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(true);
        for (int i = 0; i < count; i++) {
            tableLayout.addView(createOneFullRow());
        }
        return tableLayout;
    }

    private TableRow createOneFullRow() {
        TableRow tableRow = new TableRow(this);
        tableRow.setPadding(0, 10, 0, 0);
        int id1 = nextId;
        int id2 = nextId + 1;
        int id3 = nextId + 2;
        EditText distInput = editText(id1, "Distance");
        EditText resInput = editText(id2, "Resistance");
        TextView answer = textView(id3);
        nextId += 3;

        tableRow.addView(distInput);
        tableRow.addView(resInput);
        tableRow.addView(answer);

        TestScenario newTest = new TestScenario(distInput, resInput, answer);
        testList.add(newTest);

        return tableRow;
    }

    private EditText editText(int id, String hint) {
        EditText editText = new EditText(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        editText.setLayoutParams(lp);
        editText.setId(id);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editText.setHint(hint);
        return editText;
    }

    private TextView textView(int id) {
        TextView textView = new TextView(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        textView.setLayoutParams(lp);
        textView.setId(id);
        return textView;
    }

    public void addRowButton(View view){
        TableLayout inputTable = (TableLayout) findViewById(R.id.mainInputTable);
        inputTable.addView(createOneFullRow());

        //automatically scrolls to the bottom as new rows are added beyond the screen
        final ScrollView linearLayout = (ScrollView) findViewById(R.id.linearLayout1);

        linearLayout.postDelayed(new Runnable() {
            public void run() {
                linearLayout.fullScroll(View.FOCUS_DOWN);
            }

        }, 100L); //postDelayed is used instead of post, as sometimes the view isn't updates quickly enough and so the scroll view misses out the last entry.

    }


}
