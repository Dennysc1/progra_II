package com.ugb.myapplication;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btn;
    FloatingActionButton fab;
    TextView tempVal;
    String accion = "nuevo";
    String id="", rev="", idProd="";
    String urlCompletaFoto;
    Intent tomarFotoIntent;
    ImageView img;
    utilidades utls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utls = new utilidades();
        fab = findViewById(R.id.fabListarProd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActividad();
            }
        });
        btn = findViewById(R.id.btnGuardarAgendaProd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    tempVal = findViewById(R.id.txtpresentacion);
                    String presentacion = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtdescripcion);
                    String descripcion = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtcodigo);
                    String cod = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtmarca);
                    String marca = tempVal.getText().toString();

                    tempVal = findViewById(R.id.txtprecio);
                    String precio = tempVal.getText().toString();

                    //guardar datos en el servidor
                    JSONObject datosprod = new JSONObject();
                    if (accion.equals("modificar")) {
                        datosprod.put("_id", id);
                        datosprod.put("_rev", rev);
                    }
                    datosprod.put("idProd", idProd);
                    datosprod.put("presentacion", presentacion);
                    datosprod.put("descripcion", descripcion);
                    datosprod.put("codigo", cod);
                    datosprod.put("marca", marca);
                    datosprod.put("precio", precio);
                    datosprod.put("urlCompletaFoto", urlCompletaFoto);

                    String respuesta = "";
                    enviarDatosServidor objGuardarDatosServidor = new enviarDatosServidor(getApplicationContext());
                    respuesta = objGuardarDatosServidor.execute(datosprod.toString()).get();

                    JSONObject respuestaJSONObject = new JSONObject(respuesta);
                    if (respuestaJSONObject.getBoolean("ok")) {
                        id = respuestaJSONObject.getString("id");
                        rev = respuestaJSONObject.getString("rev");

                    } else {
                        mostrarMsg("Error al guardar datos en el servidor");
                    }
                    DB db = new DB(getApplicationContext(), "", null, 1);
                    String[] datos = new String[]{id, rev, idProd, presentacion, descripcion, cod, marca, precio, urlCompletaFoto};
                    respuesta = db.administrar_prod(accion, datos);

                    if (respuesta.equals("ok")) {
                        Toast.makeText(getApplicationContext(), "Amigo guardado con exito", Toast.LENGTH_LONG).show();
                        abrirActividad();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error al intentar guardar el amigo: " + respuesta, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        img = findViewById(R.id.btnImgprod);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFotoAmigo();
            }
        });
        mostrarDatosAmigos();
    }
    private void tomarFotoAmigo(){
        tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File fotoAmigo = null;
        try{
            fotoAmigo = crearImagenAmigo();
            if( fotoAmigo!=null ){
                Uri uriFotoamigo = FileProvider.getUriForFile(MainActivity.this,
                        "com.ugb.controlesbasicos.fileprovider", fotoAmigo);
                tomarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFotoamigo);
                startActivityForResult(tomarFotoIntent, 1);
            }else{
                mostrarMsg("No se pudo creaar la foto");
            }
        }catch (Exception e){
            mostrarMsg("Error al abrir la camara: "+ e.getMessage());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(requestCode==1 && resultCode==RESULT_OK){
                Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaFoto);
                img.setImageBitmap(imageBitmap);
            }else{
                mostrarMsg("El usuario cancelo la toma de la foto");
            }
        }catch (Exception e){
            mostrarMsg("Error añ obtener la foto de la camara");
        }
    }
    private File crearImagenAmigo() throws Exception{
        String fechaHoraMs = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()),
                fileName = "imagen_"+ fechaHoraMs +"_";
        File dirAlmacenamiento = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if( dirAlmacenamiento.exists()==false ){
            dirAlmacenamiento.mkdirs();
        }
        File imagen = File.createTempFile(fileName, ".jpg", dirAlmacenamiento);
        urlCompletaFoto = imagen.getAbsolutePath();
        return imagen;
    }
    private void mostrarDatosAmigos(){
        try{
            Bundle parametros = getIntent().getExtras();//Recibir los parametros...
            accion = parametros.getString("accion");

            if(accion.equals("modificar")){
                JSONObject jsonObject = new JSONObject(parametros.getString("productos")).getJSONObject("value");
                id = jsonObject.getString("_id");
                rev = jsonObject.getString("_rev");
                idProd = jsonObject.getString("idProd");

                tempVal = findViewById(R.id.txtpresentacion);
                tempVal.setText(jsonObject.getString("presentacion"));

                tempVal = findViewById(R.id.txtdescripcion);
                tempVal.setText(jsonObject.getString("descripcion"));

                tempVal = findViewById(R.id.txtcodigo);
                tempVal.setText(jsonObject.getString("codigo"));

                tempVal = findViewById(R.id.txtmarca);
                tempVal.setText(jsonObject.getString("marca"));

                tempVal = findViewById(R.id.txtprecio);
                tempVal.setText(jsonObject.getString("precio"));

                urlCompletaFoto = jsonObject.getString("urlCompletaFoto");
                Bitmap imageBitmap = BitmapFactory.decodeFile(urlCompletaFoto);
                img.setImageBitmap(imageBitmap);
            }else{//nuevo registro
                idProd = utls.generarIdUnico();
            }
        }catch (Exception e){
            mostrarMsg("Error al mostrar datos: "+ e.getMessage());
        }
    }
    private void mostrarMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    private void abrirActividad(){
        Intent abrirActividad = new Intent(getApplicationContext(), lista_prods.class);
        startActivity(abrirActividad);
    }
}