package com.example.igorb.earthingapp_ver2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoadTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_test);

    }

    public SQLiteDatabase openDB(String path) {

        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(path, null, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return db;

    }

    public void printData(View view){
        SQLiteDatabase db = openDB("/data/user/0/com.example.igorb.earthingapp_ver2/databases/Results");
        if(db != null) {
            Cursor c = db.rawQuery("SELECT * FROM Results", null);
            int index = c.getColumnIndex("distance");
            c.moveToFirst();
            Toast.makeText(this, "int: " + Integer.toString(c.getInt(index)), Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "null", Toast.LENGTH_LONG).show();
        }
    }
}
