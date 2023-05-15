package com.example.appbdsqlitesalvador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public EditText etCodigo, etDescripcion, etUbicacion, etExistencia;
    public Button btnAbrirListaProp, btnAudioVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCodigo = findViewById(R.id.etCodigo);
        etDescripcion = findViewById(R.id.etDescripccion);
        etUbicacion = findViewById(R.id.etUbicacion);
        etExistencia = findViewById(R.id.etExistencia);

        btnAbrirListaProp = findViewById(R.id.btnAbrirListaProp);
        btnAudioVideo = findViewById(R.id.btnAudioVideo);

        btnAbrirListaProp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirVentanaLisProd = new Intent(view.getContext(), ListaProductos.class);
                startActivityForResult(abrirVentanaLisProd, 0);
            }
        });
        btnAudioVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(view.getContext(),AudioVideo.class);
                startActivityForResult(intent1,0);
            }
        });
    }

    /**
     * Metodo para alta de producto.
     *
     * @param view
     */
    public void altaProducto(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();//objetos de base de datos se reescribible

        //para guardar valor de variables del formulario
        String Codigo = etCodigo.getText().toString();
        String Descripcion = etDescripcion.getText().toString();
        String Ubicacion = etUbicacion.getText().toString();
        String Existencia = etExistencia.getText().toString();


        //se crea contenedor para almacenar los valores
        ContentValues registro = new ContentValues();//Almacena variables de diversos tipos de datos
        //se integran variables de java con valores y campos de la tabla articulo
        registro.put("cod", Codigo);
        registro.put("descripcion", Descripcion);
        registro.put("ubicacion", Ubicacion);
        registro.put("existencia", Existencia);
        //se inserta registro en tabla articulo
        bd.insert("articulo", null, registro);
        // Se cierra BD
        bd.close();
        //Se limpian los campos de texto
        etCodigo.setText(null);
        etDescripcion.setText(null);
        etUbicacion.setText(null);
        etExistencia.setText(null);

        //Imprimir datos de registro exitoso en ventana emergente tipo TOAST
        Toast.makeText(this, "Exito al ingresar el registro\n\nCodigo:" + Codigo + "\nDescripcion:" + Descripcion + "\nUbicacion:" + Ubicacion + "Existencia:" + Existencia, Toast.LENGTH_LONG).show();
    }

    //metodo para consulta por campo distintivo
    public void consultaProducto(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();//objetos de base de datos se reescribible

        //se asigna una variable para busqueda y consulta por campo distintivo
        String codigoConsulta = etCodigo.getText().toString();
        //Cursor recorre los campos d euna tabla hasta encontralo por campo distintivo
        Cursor fila = bd.rawQuery("SELECT descripcion,ubicacion,existencia from articulo where cod=" + codigoConsulta, null);

        if (fila.moveToFirst()) {//si condicion es verdadera, es decir, encontro un campo y sus datos
            etDescripcion.setText(fila.getString(0));
            etUbicacion.setText(fila.getString(1));
            etExistencia.setText(fila.getString(2));
            Toast.makeText(this, "Registro encontrado de forma EXITOSA", Toast.LENGTH_LONG).show();
        } else {//condicion falsa si no encontro un registro
            Toast.makeText(this, "No existe Articulo con ese Codigo\nVerifica", Toast.LENGTH_LONG).show();
            bd.close();
        }

    }//termina metodo consulta producto

    //metodo para eliminar producto por campo distintivo
    public void eliminarProducto(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();//objetos de base de datos  reescribible

        //se asigna variable para busqueda por campo distitivo caodigo producto
        String codigoBaja = etCodigo.getText().toString();
        //Se genera instrtuccion SQL para que se elimine el registro de producto
        int c = bd.delete("articulo","cod="+codigoBaja,null);
        if(c==1){
            Toast.makeText(this,"Registro eliminado de BD exitoso\nVerifica Consulta",Toast.LENGTH_LONG).show();
            //Limpia cajas de texto
            this.etCodigo.setText("");
            this.etDescripcion.setText("");
            this.etUbicacion.setText("");
            this.etExistencia.setText("");
        }else{
            Toast.makeText(this,"Error\nNo existe Articulo con ese codigo",Toast.LENGTH_LONG).show();
        }

    }//termina metodo para eliminar producto

    //metodo para modificar
    public void modificarProductos(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();//objetos de base de datos  reescribible

        //se declaran variables que vienen desde formulario sus datos
        String Codigo = etCodigo.getText().toString();
        String Descripcion = etDescripcion.getText().toString();
        String Ubicacion = etUbicacion.getText().toString();
        String Existencia = etExistencia.getText().toString();

        //se genera un contenedor para almacenar los valores anteriores
        ContentValues registro = new ContentValues();
        registro.put("cod",Codigo);
        registro.put("descripcion",Descripcion);
        registro.put("ubicacion",Ubicacion);
        registro.put("existencia",Existencia);

        //Se crea la variable que contine la instruccion SQL encargada de modificar y almacenar valor 1 si edito
        int cant = bd.update("articulo",registro,"cod="+Codigo,null);
        bd.close();
        if(cant==1) {//condicion si realizo modificacion
            Toast.makeText(this,"Registro actualizado de forma correcta",Toast.LENGTH_LONG).show();
            //Se limpian los campos de texto
            etCodigo.setText(null);
            etDescripcion.setText(null);
            etUbicacion.setText(null);
            etExistencia.setText(null);

        }else {//contrario a no modificacion
            Toast.makeText(this,"Error\nNo se modifico registro",Toast.LENGTH_LONG).show();
        }
    } //termina metodo
}
