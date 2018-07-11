package com.example.a17010621.p08mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight;
    EditText etHeight;
    Button btnCalc;
    Button btnReset;
    TextView tvDate;
    TextView tvBmi;
    TextView tvDisplay;
    float weight;
    float height;
    float bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etWeight.setSelection(0);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalc = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBmi = findViewById(R.id.textViewBMI);
        tvDisplay=findViewById(R.id.textViewOutcome);

    }

    @Override
    protected void onPause() {
        super.onPause();
        weight = Float.parseFloat(etWeight.getText().toString());
        height = Float.parseFloat(etHeight.getText().toString());


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putFloat("weight", weight);
        prefEdit.putFloat("height", height);

        prefEdit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        weight = prefs.getFloat("weight", 0);
        height = prefs.getFloat("height", 0);


        Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
        String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH) + 1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);
        tvDate.setText("Last Calculated Date: " + datetime);


        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight = Float.parseFloat(etWeight.getText().toString());
                height = Float.parseFloat(etHeight.getText().toString());

                bmi = 0;
                String msg="";

                float den = height * height;
                bmi = weight / den;
                if (bmi<18.5){
                    msg="You are underweight";
                }else if (bmi <25){
                    msg="You BMI is normal";
                }else if (bmi <30){
                    msg="You are overweight";
                }else if(bmi>=30) {
                    msg = "You are obese";
                }else{
                    msg="Error in calculation";
                }

                if(weight<0 || height<0.3){

                    msg +="Error";

                }else {

                    bmi = weight / height * height;
                    msg += bmi;

                }


                tvBmi.setText("Last Calculated BMI: " + String.format("%.3f",bmi));
                tvDisplay.setText(msg);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDate.setText("Last Calculated Date: " );
                tvBmi.setText("Last Calculated BMI: " );
                tvDisplay.setText("");
                etWeight.setText("");
                etHeight.setText("");

            }
        });


    }













}