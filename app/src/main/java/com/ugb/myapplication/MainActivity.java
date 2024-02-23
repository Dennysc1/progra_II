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
              activarsensordeacelerometro();
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

       private void activarsensordeacelerometro(){
              sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
              sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
              if(sensor==null){
                     tempVal.setText("tu cell no tiene el sensor");
                     finish();
              }

              sensorEventListener = new SensorEventListener() {
                     @Override
                     public void onSensorChanged(SensorEvent Event) {
                            double valor = Event.values[0];
                            tempVal.setText("aceleomtro: x= "+ Event.values[0]+ ", y= "+ Event.values[1] +", z= "+ Event.values[2]);

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