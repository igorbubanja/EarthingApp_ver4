package com.example.igorb.earthingapp_ver2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OpeningScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_screen);
    }

    public void startNewTest(View view){
        Intent newTest = new Intent(this, MainActivity.class);
        startActivity(newTest);
    }
}
