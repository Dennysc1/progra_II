package com.ugb.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class adaptadorImagenes extends BaseAdapter {
    Context context;
    ArrayList<fernando> datosAmigosArrayList;
    fernando datosFernando;
    LayoutInflater layoutInflater;
    public adaptadorImagenes(Context context, ArrayList<fernando> datosFernandoArrayList) {
        this.context = context;
        this.datosAmigosArrayList = datosFernandoArrayList;
    }
    @Override
    public int getCount() {
        return datosAmigosArrayList.size();
    }
    @Override
    public Object getItem(int i) {
        return datosAmigosArrayList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i; //Long.parseLong(datosAmigosArrayList.get(i).getIdAmigo());
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.listview_imagenes, viewGroup, false);
        try{
            datosFernando = datosAmigosArrayList.get(i);

            double cost = Integer.parseInt(datosFernando.getCosto());
            double pre = Integer.parseInt(datosFernando.getPrecio());
            double res = (((pre-cost)/pre)*100);
            DecimalFormat formato1 = new DecimalFormat("#.00");

            TextView tempVal = itemView.findViewById(R.id.lblnombre);
            tempVal.setText(datosFernando.getCosto()+"$");

            tempVal = itemView.findViewById(R.id.lbltelefono);
            tempVal.setText(datosFernando.getStock());

            tempVal = itemView.findViewById(R.id.lblemail);
            tempVal.setText(datosFernando.getPrecio()+"$");

            tempVal = itemView.findViewById(R.id.lblpor);
            String respos = String.valueOf(formato1.format(res));
            tempVal.setText(respos+"%");


            Bitmap imageBitmap = BitmapFactory.decodeFile(datosFernando.getUrlFotoAmigo());
            ImageView img = itemView.findViewById(R.id.imgFoto);
            img.setImageBitmap(imageBitmap);
        }catch (Exception e){
            Toast.makeText(context, "Error al mostrar los datos: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return itemView;
    }
}
