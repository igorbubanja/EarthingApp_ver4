package com.example.igorb.earthingapp_ver2;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by IgorB on 22/04/2018.
 */

public class TestScenario extends MainActivity implements Serializable {

    /*These field are declared as transient as they are not serializable, so will crash the app when
     * I try to serialize instances of this class. This is a hacky solution -  use a better method at some point
     * e.g. a function that returns the double values, then just pass those values */
    transient EditText distanceInput;
    transient EditText resistanceInput;
    transient TextView answerOutput;

    double distance = -1;
    double resistance = -1;
    double answer = -1;

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

            distance = distanceDouble;
            resistance = resistanceDouble;
            answer = answerDouble;
        }
        else{
            distance = -1;
            resistance = -1;
            answer = -1;
        }
    }


    public TestScenario( EditText distInput, EditText resInput,  TextView ansText){

        distanceInput = distInput;
        resistanceInput = resInput;
        answerOutput = ansText;
    }
}

