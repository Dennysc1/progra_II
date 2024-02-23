package com.ugb.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.annotation.SuppressLint;
import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {
       TextView tempVal;
       SensorManager sensorManager;
       Sensor sensor;
       SensorEventListener sensorEventListener;


       @Override
       protected void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
              setContentView(R.layout.activity_main);
              tempVal = findViewById(R.id.lblsensoracelerometro);
              activarsensordeluz();
       }

       @Override
       protected void onResume() {
              iniciar();
              super.onResume();
       }

       @Override
       protected void onPause() {
              detener();
              super.onPause();
       }

       private void activarsensordeluz(){
              sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
              sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
              if(sensor==null){
                     tempVal.setText("tu cell no tiene el sensor");
                     finish();
              }

              sensorEventListener = new SensorEventListener() {
                     @Override
                     public void onSensorChanged(SensorEvent Event) {
                            double valor = Event.values[0];
                            tempVal.setText("proximidad"+ valor);
                            if (valor<=2){
                                   getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                            }else if (valor<=5){
                                   getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                            }else if (valor>50){
                                   getWindow().getDecorView().setBackgroundColor(Color.MAGENTA);
                            }

                     }

                     @Override
                     public void onAccuracyChanged(Sensor sensor, int accuracy) {

                     }
              };

       }
       private void iniciar(){
              sensorManager.registerListener(sensorEventListener, sensor, 2000*1000);
       }
       private void  detener(){

              sensorManager.unregisterListener(sensorEventListener);
       }


}