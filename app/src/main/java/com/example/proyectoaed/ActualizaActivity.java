package com.example.proyectoaed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectoaed.DAO.ConexionDAO;
import com.example.proyectoaed.DAO.DatosHelper.tabladatos;
import com.example.proyectoaed.DTO.DatosDTO;

public class ActualizaActivity extends AppCompatActivity {

    EditText txtnombre,txtedad,txtcorreo;
    DatosDTO datosDTO = new DatosDTO();
    Button btnaguardar, btnacancelar;
    String idactualiza="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualiza);

        Bundle datos = getIntent().getExtras();
        String idb = datos.getString("idbuscar");

        txtnombre = findViewById(R.id.txtnombreA);
        txtedad = findViewById(R.id.txtedadA);
        txtcorreo = findViewById(R.id.txtcorreoA);
        btnaguardar = findViewById(R.id.btnAactualiza);
        btnacancelar = findViewById(R.id.btnAcancelar);

        String[] parametrocondiciones = new String[]{idb};

        ConexionDAO conexionDAO = new ConexionDAO(this);
        conexionDAO.abriConexion();
        if (conexionDAO.consultaID(parametrocondiciones)){
            datosDTO = conexionDAO.regresaID();
            txtnombre.setText(datosDTO.getNombre());
            txtedad.setText(String.valueOf(datosDTO.getEdad()));
            txtcorreo.setText(datosDTO.getCorreo());
            idactualiza = String.valueOf(datosDTO.getId());
        }else{
            Toast.makeText(this, "No se puede recuperar la información", Toast.LENGTH_SHORT).show();
        }
        conexionDAO.cerrar();
    }

    public void acciones(View v){
        switch (v.getId()){
            case R.id.btnAactualiza: btnguarda();
                break;
            case R.id.btnAcancelar: btncancela();
                break;
        }
    }

    private void btnguarda(){

        String[] condiciones = new String[]{idactualiza};
        ContentValues contentValues = new ContentValues();
        contentValues.put(tabladatos.TABLA_NOMBRE,txtnombre.getText().toString());
        contentValues.put(tabladatos.TABLA_EDAD,Integer.valueOf(txtedad.getText().toString()));
        contentValues.put(tabladatos.TABLA_CORREO,txtcorreo.getText().toString());

        ConexionDAO conexionDAO = new ConexionDAO(this);
        conexionDAO.abriConexion();
        if (conexionDAO.actualiza(contentValues,condiciones)){
            Toast.makeText(this, "Se actualizaron los datos...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("vista","Actualizar");
            setResult(Activity.RESULT_OK,intent);
            finish();
        }else{
            Toast.makeText(this, "Error al actualizar...", Toast.LENGTH_SHORT).show();
            btnaguardar.setActivated(false);
        }

    }

    private void btncancela(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}