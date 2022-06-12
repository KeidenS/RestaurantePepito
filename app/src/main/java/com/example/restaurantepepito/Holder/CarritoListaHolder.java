package com.example.restaurantepepito.Holder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.restaurantepepito.Fragment.Carrito_Gestionar_Fragment;
import com.example.restaurantepepito.MainActivity;
import com.example.restaurantepepito.R;
import com.example.restaurantepepito.SQLite.DbPedido_x_Plato;

public class CarritoListaHolder extends RecyclerView.ViewHolder{

    public Context context;

    public TextView nombre;
    public TextView precio;
    public TextView cantidad;
    public TextView subtotal;


    public Integer id_pedido;
    public Integer id_plato;

    public Integer getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Integer id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Integer getId_plato() {
        return id_plato;
    }

    public void setId_plato(Integer id_plato) {
        this.id_plato = id_plato;
    }

    public CarritoListaHolder(@NonNull View itemView) {
        super(itemView);
        itemView.getContext();

        context = itemView.getContext();




        nombre = itemView.findViewById(R.id.card_carrito_pedido_nombre);
        precio = itemView.findViewById(R.id.card_carrito_pedido_precio);
        cantidad = itemView.findViewById(R.id.card_carrito_pedido_cantidad);
        subtotal = itemView.findViewById(R.id.card_carrito_pedido_subtotal);

    }






}


