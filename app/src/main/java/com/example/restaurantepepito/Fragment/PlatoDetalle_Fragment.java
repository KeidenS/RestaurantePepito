package com.example.restaurantepepito.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantepepito.MainActivity;
import com.example.restaurantepepito.R;
import com.example.restaurantepepito.SQLite.DbPedido_x_Plato;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

public class PlatoDetalle_Fragment extends Fragment {

    Context context;

    private Integer id_plato;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String imagen;

    private TextView nombre_d;
    private TextView descripcion_d;
    private TextView precio_d;
    private TextInputEditText cantidad_d;
    private Button agregar_carrito;
    private ImageView lista_imagen_plato;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle datosRecuperados = getArguments();
        if (datosRecuperados != null) {
            id_plato = datosRecuperados.getInt("id_plato");
            descripcion = datosRecuperados.getString("descripcion");
            nombre = datosRecuperados.getString("nombre");
            precio = datosRecuperados.getDouble("precio");
            imagen = datosRecuperados.getString("imagen");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plato_detalle, container, false);

        nombre_d = view.findViewById(R.id.detalle_plato_nombre);
        descripcion_d = view.findViewById(R.id.detalle_plato_descripcion);
        precio_d = view.findViewById(R.id.detalle_plato_precio);
        cantidad_d = view.findViewById(R.id.detalle_plato_cantidad);
        agregar_carrito = view.findViewById(R.id.detalle_plato_boton_agregar);
        lista_imagen_plato = view.findViewById(R.id.lista_imagen_plato);

        Picasso.get().load(imagen).error(R.mipmap.ic_launcher).into(lista_imagen_plato);

        nombre_d.setText(nombre);
        descripcion_d.setText(descripcion);
        precio_d.setText(precio.toString());

        agregar_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre_insertar = nombre;
                Double precio_insertar = precio;
                Integer cantidad_insertar = Integer.parseInt(cantidad_d.getText().toString());
                Double subtotal_insertar = precio_insertar * cantidad_insertar;
                Integer id_plato_insertar = id_plato;

                MainActivity myActivity = (MainActivity)context;

                DbPedido_x_Plato db_pedido = new DbPedido_x_Plato(getContext());
                long id = db_pedido.insertarPedido(nombre_insertar,precio_insertar,cantidad_insertar,subtotal_insertar,id_plato_insertar);

                if(id > 0){
                    Toast.makeText(getContext(), "PRODUCTO AGREGADO AL CARRITO", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "ERROR AL AGREGAR PRODUCTO", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}