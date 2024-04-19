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

import java.util.ArrayList;

public class adaptadorImagenes extends BaseAdapter {
    Context context;
    ArrayList<productos> datosProdsArrayList;
    productos datosprods;
    LayoutInflater layoutInflater;
    public adaptadorImagenes(Context context, ArrayList<productos> datosProdsArrayList) {
        this.context = context;
        this.datosProdsArrayList = datosProdsArrayList;
    }
    @Override
    public int getCount() {
        return datosProdsArrayList.size();
    }
    @Override
    public Object getItem(int i) {
        return datosProdsArrayList.get(i);
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
            datosprods = datosProdsArrayList.get(i);

            TextView tempVal = itemView.findViewById(R.id.lblpresentacion);
            tempVal.setText(datosprods.getPresentacion());

            tempVal = itemView.findViewById(R.id.lblprecio);
            tempVal.setText(datosprods.getPrecio());

            tempVal = itemView.findViewById(R.id.lblmarca);
            tempVal.setText(datosprods.getMarca());

            Bitmap imageBitmap = BitmapFactory.decodeFile(datosprods.getUrlFotoProd());
            ImageView img = itemView.findViewById(R.id.imgFoto);
            img.setImageBitmap(imageBitmap);
        }catch (Exception e){
            Toast.makeText(context, "Error al mostrar los datos: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return itemView;
    }
}
