package com.example.proyectoaed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectoaed.DTO.DatosDTO;
import com.example.proyectoaed.DAO.ConexionDAO;

public class EliminaActivity extends AppCompatActivity {

    EditText txtnombre,txtedad,txtcorreo;
    DatosDTO datosDTO = new DatosDTO();
    Button btnelimina, btncancelar;
    String idelimina="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elimina);

        Bundle datos = getIntent().getExtras();
        String idb = datos.getString("idbuscar");

        txtnombre = findViewById(R.id.txtnombreE);
        txtedad = findViewById(R.id.txtedadE);
        txtcorreo = findViewById(R.id.txtcorreoE);
        btnelimina = findViewById(R.id.btnEelimina);
        btncancelar = findViewById(R.id.btnEcancelar);

        String[] parametrocondiciones = new String[]{idb};

        ConexionDAO conexionDAO = new ConexionDAO(this);
        conexionDAO.abriConexion();
        if (conexionDAO.consultaID(parametrocondiciones)){
            datosDTO = conexionDAO.regresaID();
            txtnombre.setText(datosDTO.getNombre());
            txtedad.setText(String.valueOf(datosDTO.getEdad()));
            txtcorreo.setText(datosDTO.getCorreo());
            idelimina = String.valueOf(datosDTO.getId());
        }else{
            Toast.makeText(this, "No se puede recuperar la información", Toast.LENGTH_SHORT).show();
        }
        conexionDAO.cerrar();
    }

    public void acciones(View v){
        switch (v.getId()){
            case R.id.btnEelimina: eliminacion();
                break;
            case R.id.btnEcancelar: cancelar();
                break;

        }
    }

    private void eliminacion(){
        String[] condiciones = new String[]{idelimina};
        ConexionDAO conexionDAO = new ConexionDAO(this);
        conexionDAO.abriConexion();
        if (conexionDAO.eliminar(condiciones)){
            Toast.makeText(this, "Se elimino correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("vista","Elimina");
            setResult(Activity.RESULT_OK,intent);
            finish();
        }else{
            Toast.makeText(this, "Error al tratar de eliminar", Toast.LENGTH_SHORT).show();
            btnelimina.setActivated(false);
        }
    }

    private void cancelar(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}