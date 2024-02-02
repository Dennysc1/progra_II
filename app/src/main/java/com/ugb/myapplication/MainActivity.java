package com.ugb.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tempVal;
    Button btn;

    RadioGroup opt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnCalcular);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempVal = findViewById(R.id.txtnum1);
                double num1 = Double.parseDouble(tempVal.getText().toString());

                tempVal = findViewById(R.id.txtnum2);
                double num2 = Double.parseDouble(tempVal.getText().toString());


                double respuesta=0;
                opt = findViewById(R.id.opt0pciones);
                if(opt.getCheckedRadioButtonId()==R.id.optSuma){
                    respuesta = num1+num2;
                }
                if(opt.getCheckedRadioButtonId()==R.id.optResta){
                    respuesta = num1-num2;
                }
                if(opt.getCheckedRadioButtonId()==R.id.optMultiplicar){
                    respuesta = num1*num2;
                }
                if(opt.getCheckedRadioButtonId()==R.id.optDividir){
                    respuesta = num1/num2;
                }
                if(opt.getCheckedRadioButtonId()==R.id.optPorcentaje){
                    respuesta = (num1*num2)/100;
                }
                if(opt.getCheckedRadioButtonId()==R.id.optExponencial){
                    Math math = null;
                    respuesta =Math.pow(num1,num2);
                }
                if(opt.getCheckedRadioButtonId()==R.id.optFactorial){
                    num2 = 0;
                    double factorial = 1;
                    for (double i = 1; i <= num1; i++) {
                        factorial *= i;
                    }
                    respuesta = factorial;
                }
                if(opt.getCheckedRadioButtonId()==R.id.optRaiz){
                    Math math = null;
                    respuesta = Math.pow(num1, 0.5);
                }

                tempVal = findViewById(R.id.lblrespuesta);
                tempVal.setText("Respuesta: " + respuesta);
            }
        });
    }
}