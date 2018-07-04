//version 4
package com.example.igorb.earthingapp_ver2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<TestScenario> testList = new ArrayList<>();
    int testNumber = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.save:
                //call save function here
                    save();
                return true;
            default:
                return false;
            }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Dynamically setting up the UI
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView1);
        TableLayout inputTable = tableLayout(0);
        inputTable.setId(R.id.mainInputTable); //mainInputTable is defined as an id in ids.xml in the res folder
        scrollView.addView(inputTable);
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
        testNumber++;
        TableRow tableRow = new TableRow(this);
        tableRow.setPadding(0, 10, 0, 0);
        TextView testNumberView = textView(0, 0.2f);
        testNumberView.setText(Integer.toString(testNumber));
        EditText distInput = editText(1, "Distance");
        EditText resInput = editText(2, "Resistance");
        TextView answer = textView(3, 1f);
        tableRow.addView(testNumberView);
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

    private TextView textView(int id, float weight) {
        TextView textView = new TextView(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, weight);
        textView.setLayoutParams(lp);
        textView.setId(id);
        return textView;
    }

    public void addRowButton(View view){
        TableLayout inputTable = (TableLayout) findViewById(R.id.mainInputTable);
        TableRow tableRowToAdd = createOneFullRow();
        inputTable.addView(tableRowToAdd);

        /*
        //automatically scrolls to the bottom as new rows are added beyond the screen
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView1);
        scrollView.postDelayed(new Runnable() {
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }

        }, 100L); //postDelayed is used instead of post, as sometimes the view isn't updates quickly enough and so the scroll view misses out the last entry.
        */

        //Force the focus onto the new row
        EditText lastEditText = (EditText) tableRowToAdd.getChildAt(1);
        lastEditText.requestFocus();

    }

    public void save(){
        //TODO: create a database helper class
        //Using SQLite
        SQLiteDatabase myDataBase = this.openOrCreateDatabase("Results", MODE_PRIVATE, null);
        myDataBase.execSQL("DROP TABLE Results"); //only use this during testing
        myDataBase.execSQL("CREATE TABLE IF NOT EXISTS Results(distance REAL(3), resistance REAL(3), resistivity REAL(3))");
        //myDataBase.execSQL("INSERT INTO Results (distance, resistance, resistivity) VALUES (" + testList.get(0).distance + ", 1, 1)");
        for(TestScenario test:testList){
            myDataBase.execSQL("INSERT INTO Results (distance, resistance, resistivity) VALUES (" +
                    test.distance + "," +  test.resistance + "," + test.answer + ")");
        }

        //Toast.makeText(this, myDataBase.getPath(), Toast.LENGTH_LONG).show();
    }

}
