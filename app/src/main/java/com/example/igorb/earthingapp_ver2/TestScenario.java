package com.example.igorb.earthingapp_ver2;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by IgorB on 22/04/2018.
 */

public class TestScenario extends MainActivity {
    EditText distanceInput;
    EditText resistanceInput;
    TextView answerOutput;

    double calcFunction(double a, double b){
        return a + b;
    }

    void calculate(){
        String distString = distanceInput.getText().toString();
        String resString = resistanceInput.getText().toString();

        if(distString.length() != 0 && resString.length() != 0){
            Double distanceDouble = Double.parseDouble(distString);
            Double resistanceDouble = Double.parseDouble(resString);
    
            Double answerDouble = calcFunction(distanceDouble, resistanceDouble);
            answerOutput.setText(Double.toString(answerDouble));
        }
    }

    public TestScenario( EditText distInput, EditText resInput,  TextView ansText){

        distanceInput = distInput;
        resistanceInput = resInput;
        answerOutput = ansText;
    }
}
