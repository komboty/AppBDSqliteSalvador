package com.example.appbdsqlitesalvador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListaProductos extends AppCompatActivity {
    public TextView tvSalidaPrecio;
    public ListView lvProductos;

    public String productos[] = {"Smart TV", "Tablet", "PC", "Radio", "Smart watch"};
    public String precio[] = {"10000", "900", "4000", "500", "300"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        // Integrar elementos
        tvSalidaPrecio = findViewById(R.id.tvSalidaPrecio);
        lvProductos = (ListView) findViewById(R.id.lvProductos);

        // Definicion de adapter para lista de productos.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, productos);
        lvProductos.setAdapter(adapter);

        // onclick para seleccionar elemento
        lvProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                tvSalidaPrecio.setText("Precio de venta: " + precio[position]);
            }
        });
    }
}
