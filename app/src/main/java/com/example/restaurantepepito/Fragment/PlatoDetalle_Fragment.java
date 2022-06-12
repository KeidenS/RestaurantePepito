package com.example.restaurantepepito.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantepepito.MainActivity;
import com.example.restaurantepepito.Model.Pedido_x_PlatopModel;
import com.example.restaurantepepito.NavigationHost;
import com.example.restaurantepepito.R;
import com.example.restaurantepepito.SQLite.DbPedido_x_Plato;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    private TextView cantidad_d;
    private Button agregar_carrito;
    private ImageView lista_imagen_plato;

    private Button sumar_cantidad;
    private Button restar_cantidad;
    private TextView monto_text;

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
        sumar_cantidad = view.findViewById(R.id.detalle_plato_sumar);
        restar_cantidad = view.findViewById(R.id.detalle_plato_restar);
        monto_text = view.findViewById(R.id.text_monto_detalle_plato);

        Picasso.get().load(imagen).error(R.mipmap.ic_launcher).into(lista_imagen_plato);

        nombre_d.setText(nombre);
        descripcion_d.setText(descripcion);


        precio_d.setText("S/"+String.format("%.2f",precio));

        sumar_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer cantidad_suma = 1+Integer.parseInt(cantidad_d.getText().toString());
                cantidad_d.setText(cantidad_suma.toString());

                Double monto = Integer.parseInt(cantidad_d.getText().toString()) * precio;
                monto_text.setText("S/"+String.format("%.2f",monto));

            }
        });

        restar_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!cantidad_d.getText().toString().equals("0")){
                    Integer cantidad_resta = Integer.parseInt(cantidad_d.getText().toString())-1;
                    cantidad_d.setText(cantidad_resta.toString());

                    Double monto = Integer.parseInt(cantidad_d.getText().toString()) * precio;
                    monto_text.setText("S/"+String.format("%.2f",monto));
                }



            }
        });

        agregar_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!cantidad_d.getText().toString().equals("0")){
                    String nombre_insertar = nombre;
                    Double precio_insertar = precio;
                    Integer cantidad_insertar = Integer.parseInt(cantidad_d.getText().toString());
                    Double subtotal_insertar = precio_insertar * cantidad_insertar;
                    Integer id_plato_insertar = id_plato;

                    MainActivity myActivity = (MainActivity)context;
                    DbPedido_x_Plato db_pedido = new DbPedido_x_Plato(getContext());


                    Pedido_x_PlatopModel pedido = db_pedido.buscar_Pedidos(id_plato);

                    boolean pedido_s = pedido.getId_pedido() == null;

                    if(pedido_s == false){
                        int cantidad_m = pedido.getCantidad() + cantidad_insertar;
                        double subtotal_m = precio_insertar * cantidad_m;
                        db_pedido.actualizarPedido(pedido.getId_pedido(),cantidad_m,subtotal_m);
                        ((NavigationHost) getActivity()).toastCorrecto("Exito, producto agregado");
                        cantidad_d.setText("0");
                    }
                    else{
                        long id = db_pedido.insertarPedido(nombre_insertar,precio_insertar,cantidad_insertar,subtotal_insertar,id_plato_insertar);
                        if(id > 0){
                            ((NavigationHost) getActivity()).toastCorrecto("Exito, producto agregado");
                            cantidad_d.setText("0");
                        }else{
                            ((NavigationHost) getActivity()).toastCorrecto("Exito, producto agregado");
                        }
                    }


                }

                else{
                    ((NavigationHost) getActivity()).toastIncorrecto("Error, cantidad invalida");

                }



            }
        });

        return view;
    }

    public ArrayList<Pedido_x_PlatopModel> obtener_lista(){
        DbPedido_x_Plato db_pedido = new DbPedido_x_Plato(getContext());
        return db_pedido.mostrarPedidos();
    }


}