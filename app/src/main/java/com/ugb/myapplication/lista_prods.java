package com.ugb.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class lista_prods extends AppCompatActivity {
    Bundle paramatros = new Bundle();
    DB db;
    ListView lts;
    Cursor cprods;
    final ArrayList<productos> alprod = new ArrayList<productos>();
    final ArrayList<productos> alprodsCopy = new ArrayList<productos>();
    productos datosprods;
    FloatingActionButton btn;
    JSONArray datosJSON; //para los datos que vienen del servidor.
    JSONObject jsonObject;
    obtenerDatosServidor datosServidor;
    detectarInternet di;
    int posicion = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_prods);
        db = new DB(getApplicationContext(),"", null, 1);
        btn = findViewById(R.id.fabAgregarProds);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paramatros.putString("accion","nuevo");
                abrirActividad(paramatros);
            }
        });
        di = new detectarInternet(getApplicationContext());
        if( di.hayConexionInternet() ){
            obtenerDatosProdsServidor();
        }else{
            mostrarMsg("No hay conexion, datos en local");
            obtenerAmigos();//offline
        }
        buscarAmigos();
    }
    private void obtenerDatosProdsServidor(){
        try{
            datosServidor = new obtenerDatosServidor();
            String data = datosServidor.execute().get();

            jsonObject =new JSONObject(data);
            datosJSON = jsonObject.getJSONArray("rows");
            mostrarDatosProd();
        }catch (Exception e){
            mostrarMsg("Error al obtener datos amigos del server: "+ e.getMessage());
        }
    }
    private void mostrarDatosProd(){
        try{
            if( datosJSON.length()>0 ){
                lts = findViewById(R.id.ltsProductos);
                alprod.clear();
                alprodsCopy.clear();

                JSONObject misDatosJSONObject;
                for (int i=0; i<datosJSON.length(); i++){
                    misDatosJSONObject = datosJSON.getJSONObject(i).getJSONObject("value");
                    datosprods = new productos(
                            misDatosJSONObject.getString("_id"),
                            misDatosJSONObject.getString("_rev"),
                            misDatosJSONObject.getString("idProd"),
                            misDatosJSONObject.getString("presentacion"),
                            misDatosJSONObject.getString("descripcion"),
                            misDatosJSONObject.getString("codigo"),
                            misDatosJSONObject.getString("marca"),
                            misDatosJSONObject.getString("precio"),
                            misDatosJSONObject.getString("urlCompletaFoto")
                    );
                    alprod.add(datosprods);
                }
                alprodsCopy.addAll(alprod);

                adaptadorImagenes adImagenes = new adaptadorImagenes(getApplicationContext(), alprod);
                lts.setAdapter(adImagenes);

                registerForContextMenu(lts);
            }else{
                mostrarMsg("No hay datos que mostrar");
            }
        }catch (Exception e){
            mostrarMsg("Error al mostrar datos: "+ e.getMessage());
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);
        try {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            posicion = info.position;
            menu.setHeaderTitle("Que deseas hacer con " + datosJSON.getJSONObject(posicion).getJSONObject("value").getString("presentacion"));
        }catch (Exception e){
            mostrarMsg("Error al mostrar el menu: "+ e.getMessage());
        }
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try{
            if (item.getItemId() == R.id.mnxagregar) {
                paramatros.putString("accion", "nuevo");
                abrirActividad(paramatros);
            } else if (item.getItemId() == R.id.mnxmodificar) {
                paramatros.putString("accion", "modificar");
                paramatros.putString("amigos", datosJSON.getJSONObject(posicion).toString());
                abrirActividad(paramatros);
            } else if (item.getItemId() == R.id.mnxeliminar) {
                eliminarAmigo();
            }
            return true;
        }catch (Exception e){
            mostrarMsg("Error al seleccionar el item: "+ e.getMessage());
            return super.onContextItemSelected(item);
        }
    }
    private void eliminarAmigo(){
        try {
            AlertDialog.Builder confirmar = new AlertDialog.Builder(lista_prods.this);
            confirmar.setTitle("Esta seguro de eliinar a: ");
            confirmar.setMessage(datosJSON.getJSONObject(posicion).getJSONObject("value").getString("presentacion"));
            confirmar.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        String respuesta = db.administrar_prod("eliminar", new String[]{"", "", datosJSON.getJSONObject(posicion).getJSONObject("value").getString("idProd")});
                        if (respuesta.equals("ok")) {
                            mostrarMsg("producto eliminado con exito");
                            obtenerAmigos();
                        } else {
                            mostrarMsg("Error al eliminar el amigo: " + respuesta);
                        }
                    }catch (Exception e){
                        mostrarMsg("Error al eliminar datos: "+ e.getMessage());
                    }
                }
            });
            confirmar.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            confirmar.create().show();
        }catch (Exception e){
            mostrarMsg("Error al eliminar: "+ e.getMessage());
        }
    }
    private void buscarAmigos(){
        TextView tempVal = findViewById(R.id.txtBuscarprods);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    alprod.clear();
                    String valor = tempVal.getText().toString().trim().toLowerCase();
                    if( valor.length()<=0 ){
                        alprod.addAll(alprodsCopy);
                    }else{
                        for (productos producto : alprodsCopy){
                            String presentacion = producto.getPresentacion();
                            String descripcion = producto.getDescripcion();
                            String codigo = producto.getCodigo();
                            String marca = producto.getMarca();
                            if( presentacion.trim().toLowerCase().contains(valor) ||
                                    descripcion.trim().toLowerCase().contains(valor) ||
                                    codigo.trim().contains(valor) ||
                                    marca.trim().toLowerCase().contains(valor)){
                                alprod.add(producto);
                            }
                        }
                        adaptadorImagenes adImagenes=new adaptadorImagenes(getApplicationContext(), alprod);
                        lts.setAdapter(adImagenes);
                    }
                }catch (Exception e){
                    mostrarMsg("Error al buscar: "+ e.getMessage());
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void abrirActividad(Bundle parametros){
        Intent abrirActividad = new Intent(getApplicationContext(), MainActivity.class);
        abrirActividad.putExtras(parametros);
        startActivity(abrirActividad);
    }
    private void obtenerAmigos(){ //offline
        try{
            cprods = db.obtener_prod();
            if( cprods.moveToFirst() ){
                datosJSON = new JSONArray();
                do{
                    jsonObject = new JSONObject();
                    JSONObject jsonObjectValue = new JSONObject();

                    jsonObject.put("_id", cprods.getString(0));
                    jsonObject.put("_rev", cprods.getString(1));
                    jsonObject.put("idProd", cprods.getString(2));
                    jsonObject.put("presentacion", cprods.getString(3));
                    jsonObject.put("descripcion", cprods.getString(4));
                    jsonObject.put("codigo", cprods.getString(5));
                    jsonObject.put("marca", cprods.getString(6));
                    jsonObject.put("precio", cprods.getString(7));
                    jsonObject.put("urlCompletaFoto", cprods.getString(8));
                    jsonObjectValue.put("value", jsonObject);

                    datosJSON.put(jsonObjectValue);
                }while (cprods.moveToNext());
                mostrarMsg("Punto");
                mostrarDatosProd();
            }else {
                paramatros.putString("accion", "nuevo");
                abrirActividad(paramatros);
                mostrarMsg("No hay Datos.");
            }
        }catch (Exception e){
            mostrarMsg("Error al obtener los productos: "+ e.getMessage());
        }
    }
    private void mostrarMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}