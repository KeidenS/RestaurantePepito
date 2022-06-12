package com.example.restaurantepepito.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantepepito.Adapter.CarritoListaAdapter;
import com.example.restaurantepepito.Custom.Parametros;
import com.example.restaurantepepito.MainActivity;
import com.example.restaurantepepito.Model.Pedido_x_PlatopModel;
import com.example.restaurantepepito.NavigationHost;
import com.example.restaurantepepito.R;
import com.example.restaurantepepito.SQLite.DbPedido_x_Plato;

import java.util.ArrayList;

public class Carrito_Gestionar_Fragment extends Fragment {

    private Button seguir_comprando;
    private Button realizar_pedido;
    private TextView precio_total;
    private LinearLayout carrito_vacion;
    private RecyclerView carrit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);


        seguir_comprando = view.findViewById(R.id.carrito_boton_seguir_comprando);
        realizar_pedido = view.findViewById(R.id.carrito_boton_realizar_pedido);
        precio_total = view.findViewById(R.id.carrito_monto_total);
        carrito_vacion = view.findViewById(R.id.carrito_carrito_vacio);
        carrit = view.findViewById(R.id.rv_carrito_lista_grid);
        cargar_recycler_view(view);



        precio_total.setText("S/"+String.format("%.2f",cargar_monto()));
        comprobar_carrito();

        seguir_comprando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new Plato_Lista_Fragment(),true);
            }
        });

        realizar_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!precio_total.getText().toString().equals("S/0.00")){
                    ((NavigationHost) getActivity()).navigateTo(new Pedido_Tipo_Fragment(),true);
                }
                else{
                    ((NavigationHost) getActivity()).toastIncorrecto("Error, carrito vacio");
                }



            }
        });







        return view;
    }

    public ArrayList<Pedido_x_PlatopModel> obtener_lista(){
        DbPedido_x_Plato db_pedido = new DbPedido_x_Plato(getContext());
        return db_pedido.mostrarPedidos();
    }

    public void cargar_recycler_view(View view){
        ArrayList<Pedido_x_PlatopModel> lista= obtener_lista();
        RecyclerView recyclerView = view.findViewById(R.id.rv_carrito_lista_grid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false));

        CarritoListaAdapter adapter = new CarritoListaAdapter(lista, new Parametros() {
            @Override
            public void onParametrosClicked(int position, int id_pedido) {

                MainActivity myActivity = (MainActivity)getContext();

                DbPedido_x_Plato db_pedido = new DbPedido_x_Plato(myActivity);
                int id_p = id_pedido;
                db_pedido.eliminarPedido(id_p);
                precio_total.setText("S/"+String.format("%.2f",cargar_monto()));





            }
        });



        recyclerView.setAdapter(adapter);


    }

    public double cargar_monto(){
        Double monto_total = 0.0;
        ArrayList<Pedido_x_PlatopModel> lista = obtener_lista();
        for (Pedido_x_PlatopModel p:lista) {
           monto_total = p.getSubtotal() + monto_total;

        }
        return monto_total;
    }

    public void comprobar_carrito(){
        ArrayList<Pedido_x_PlatopModel> lista = obtener_lista();
        if(lista.size() == 0){
            carrit.setVisibility(View.GONE);
        }
        else{
            carrito_vacion.setVisibility(View.GONE);
        }


    }


}
