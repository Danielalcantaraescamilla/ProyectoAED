package com.example.proyectoaed.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.proyectoaed.DTO.DatosDTO;

import java.util.ArrayList;
import java.util.List;

import com.example.proyectoaed.DAO.DatosHelper.tabladatos;


public class ConexionDAO {

    SQLiteDatabase basedatos;
    DatosHelper datosHelper;
    Context context;
    List<DatosDTO> listado = new ArrayList<DatosDTO>();
    DatosDTO datosID = new DatosDTO();

    public ConexionDAO(Context context){
        this.context = context;
    }

    public ConexionDAO abriConexion(){
        datosHelper = new DatosHelper(context);
        basedatos = datosHelper.getWritableDatabase();
        return this;
    }

    public void cerrar(){
        datosHelper.close();
    }

    public boolean insertar(ContentValues contentValues){
        boolean estado = true;
        int afectados = (int) basedatos.insert(tabladatos.TABLA_NAME,null,contentValues);
        if (afectados < 0) estado = false;
        return estado;
    }

    public boolean cargartodos(){
        boolean estado = true;
        String[] columnas = {tabladatos.TABLA_id,tabladatos.TABLA_NOMBRE,tabladatos.TABLA_EDAD,tabladatos.TABLA_CORREO};
        DatosDTO datos;
        // Cursor curso = basedatos.rawQuery("select * from " + tabladatos.TABLA,null);
        Cursor cursor = basedatos.query(tabladatos.TABLA_NAME,columnas,null,null,null,null,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                datos = new DatosDTO();
                datos.setId(Integer.parseInt(cursor.getString(0)));
                datos.setNombre(cursor.getString(1));
                datos.setEdad(Integer.parseInt(cursor.getString(2)));
                datos.setCorreo(cursor.getString(3));
                listado.add(datos);
                cursor.moveToNext();
            }
        }else if (cursor.getCount() == 0){}
        else estado = false;
        return estado;
    }

    public boolean consultaID(String[] parametrocondicion){
        boolean estado = true;
        String[] columnas = {tabladatos.TABLA_id,tabladatos.TABLA_NOMBRE,tabladatos.TABLA_EDAD,tabladatos.TABLA_CORREO};
        String condicion = tabladatos.TABLA_id + " = ?";
        Cursor cursor = basedatos.query(tabladatos.TABLA_NAME,columnas,condicion,parametrocondicion,null,null,null,null);
        //Cursor cursor = basedatos.rawQuery("select * from " + tabladatos.TABLA_NAME + " where " + tabladatos.TABLA_id + " = " + Integer.parseInt(condicion),null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            datosID.setId(Integer.parseInt(cursor.getString(0)));
            datosID.setNombre(cursor.getString(1));
            datosID.setEdad(Integer.parseInt(cursor.getString(2)));
            datosID.setCorreo(cursor.getString(3));
        }else if (cursor.getCount() == 0){}
        else {
            estado=false;
        }
        return  estado;
    }

    public boolean actualiza(ContentValues contentValues, String[] condiciones){
        boolean estado = true;
        String condicion = tabladatos.TABLA_id + " = ?";
        int afectados = (int) basedatos.update(tabladatos.TABLA_NAME,contentValues,condicion,condiciones);
        if (!(afectados>=0)) estado = false;
        return  estado;
    }

    public boolean eliminar(String[] condiciones){
        boolean estado = true;
        String condicion = tabladatos.TABLA_id +" = ?";
        int afectados = (int) basedatos.delete(tabladatos.TABLA_NAME,condicion,condiciones);
        if (!(afectados>=0)) estado = false;
        return estado;
    }

    public List<DatosDTO> getListado(){
        return listado;
    }

    public DatosDTO regresaID(){
        return  datosID;
    }
}
