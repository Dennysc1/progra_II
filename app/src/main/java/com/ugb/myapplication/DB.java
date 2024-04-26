package com.ugb.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    private static final String dbname="fernando";
    private static final int v=1;
    private static final String SQLdb = "CREATE TABLE fernando (id text, rev text, idcos text, costo text, " +
            "precio text, stock text, foto text, actualizado text)";
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, v);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLdb);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //actualizar la estrucutra de la BD.
    }
    public String administrar_amigos(String accion, String[] datos){
        try {
            SQLiteDatabase db = getWritableDatabase();
            String sql = "";
            if (accion.equals("nuevo")) {
                sql = "INSERT INTO fernando(id,rev,idcos,costo,precio,stock,foto,actualizado) VALUES('"+ datos[0] +"','"+ datos[1] +"', '" + datos[2] +
                        "','" + datos[3] + "','" + datos[4] + "','" + datos[5] + "','" + datos[6] + "', '"+ datos[7] +"')";
            } else if (accion.equals("modificar")) {
                sql = "UPDATE fernando SET id='"+datos[0]+"', rev='"+datos[1]+"', costo='" + datos[3] + "',precio='" + datos[4] + "',stock='" +
                        datos[5] + "', foto='"+ datos[6] +"', actualizado='"+ datos[7] +"' WHERE idcos='" + datos[2] + "'";
            } else if (accion.equals("eliminar")) {
                sql = "DELETE FROM fernando WHERE idcos='" + datos[2] + "'";
            }
            db.execSQL(sql);
            return "ok";
        }catch (Exception e){
            return e.getMessage();
        }
    }
    public Cursor consultar_amigos(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM fernando ORDER BY costo", null);
        return cursor;
    }
    public Cursor pendienteSincronizar(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM fernando WHERE actualizado='no'", null);
        return cursor;
    }
}