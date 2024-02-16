package com.ugb.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.annotation.SuppressLint;

public class MainActivity extends AppCompatActivity {

    TabHost tbh;
    TextView tempVal;
    Spinner spnDe, spnA;
    Spinner spnDeAlmacenamiento, spnAAlmacenamiento;
    Spinner spnDeMonedas, spnAMonedas;
    EditText txtCantidadLongitud; //
    EditText txtCantidadAlmacenamiento;
    EditText txtCantidadMonedas;
    Button btn;
    conversores miobj = new conversores();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbh = findViewById(R.id.tbhconversor);
        tbh.setup();

        tbh.addTab(tbh.newTabSpec("LON").setContent(R.id.tablongitud).setIndicator(getString(R.string.longitud)));
        tbh.addTab(tbh.newTabSpec("ALM").setContent(R.id.tabalmacenamiento).setIndicator(getString(R.string.almacenamiento)));
        tbh.addTab(tbh.newTabSpec("MON").setContent(R.id.tabmonedas).setIndicator(getString(R.string.monedas)));
        // Agregar pestañas adicionales para masa, volumen y tiempo si lo deseas

        btn = findViewById(R.id.btnConvertirLongitud);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnDe = findViewById(R.id.spnDelongitud);
                int de = spnDe.getSelectedItemPosition();

                spnA = findViewById(R.id.spnAlongitud);
                int a = spnA.getSelectedItemPosition();

                tempVal = findViewById(R.id.txtCantidadLongitud);
                double cantidad = Double.parseDouble(tempVal.getText().toString());

                double resp = miobj.convertir(0, de, a, cantidad); // Se cambia a convertir(0, de, a, cantidad) para la conversión de longitud
                Toast.makeText(getApplicationContext(),"Respuesta: "+ resp, Toast.LENGTH_LONG).show();
            }
        });

        // Botón de conversión de almacenamiento
        Button btnConvertirAlmacenamiento = findViewById(R.id.btnConvertirAlmacenamiento);
        btnConvertirAlmacenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnDeAlmacenamiento = findViewById(R.id.spnDeAlmacenamiento);
                int de = spnDeAlmacenamiento.getSelectedItemPosition();

                spnAAlmacenamiento = findViewById(R.id.spnAAlmacenamiento);
                int a = spnAAlmacenamiento.getSelectedItemPosition();

                txtCantidadAlmacenamiento = findViewById(R.id.txtCantidadAlmacenamiento);
                double cantidad = Double.parseDouble(txtCantidadAlmacenamiento.getText().toString());

                double resp = miobj.convertirAlmacenamiento(de, a, cantidad);
                Toast.makeText(getApplicationContext(),"Respuesta almacenamiento: "+ resp, Toast.LENGTH_LONG).show();
            }
        });

        // Botón de conversión de monedas
        Button btnConvertirMonedas = findViewById(R.id.btnConvertirMonedas);
        btnConvertirMonedas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnDeMonedas = findViewById(R.id.spnDeMonedas);
                int de = spnDeMonedas.getSelectedItemPosition();

                spnAMonedas = findViewById(R.id.spnAMonedas);
                int a = spnAMonedas.getSelectedItemPosition();

                txtCantidadMonedas = findViewById(R.id.txtCantidadMonedas);
                double cantidad = Double.parseDouble(txtCantidadMonedas.getText().toString());

                double resp = miobj.convertirMonedas(de, a, cantidad);
                Toast.makeText(getApplicationContext(),"Respuesta monedas: "+ resp, Toast.LENGTH_LONG).show();
            }
        });
    }
}

class conversores {
    double[][] valores = {
            // Longitud
            {1, 100, 39.3701, 3.28084, 1.1963081929167, 1.09361, 0.001, 0.000621371},
            // Almacenamiento
            {1, 0.001, 0.000001, 0.000000001},
            // Monedas
            {1, 0.85, 109.18, 0.72, 0.78, 0.91, 0.93, 20.22},
            // Masa
            {1, 0.001, 0.00220462},
            // Volumen
            {1, 1000, 0.001, 0.000000001},
            // Tiempo
            {1, 60, 3600, 86400, 604800, 2.628e+6, 3.154e+7}
    };

    public double convertir(int opcion, int de, int a, double cantidad) {
        return valores[opcion][a] / valores[opcion][de] * cantidad;
    }

    // Agregar métodos adicionales para conversión de otras unidades según sea necesario

    public double convertirAlmacenamiento(int de, int a, double cantidad) {
        return valores[1][a] / valores[1][de] * cantidad;
    }

    public double convertirMonedas(int de, int a, double cantidad) {
        return valores[2][a] / valores[2][de] * cantidad;
    }
}