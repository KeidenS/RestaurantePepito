package com.example.restaurantepepito.Holder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.restaurantepepito.MainActivity;
import com.example.restaurantepepito.R;
import com.example.restaurantepepito.SQLite.DbPedido_x_Plato;

public class CarritoListaHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

    public Context context;

    public TextView nombre;
    public TextView precio;
    public TextView cantidad;
    public TextView subtotal;
    public Button eliminar;
    public LinearLayout contenedor;

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



        contenedor  = itemView.findViewById(R.id.card_carrito_contenedor);
        nombre = itemView.findViewById(R.id.card_carrito_nombre);
        precio = itemView.findViewById(R.id.card_carrito_precio);
        cantidad = itemView.findViewById(R.id.card_carrito_cantidad);
        subtotal = itemView.findViewById(R.id.card_carrito_subtotal);
        eliminar = itemView.findViewById(R.id.card_carrito_boton_eliminar);
    }

    @Override
    public void onClick(View view) {

        MainActivity myActivity = (MainActivity)context;

        DbPedido_x_Plato db_pedido = new DbPedido_x_Plato(myActivity);

        Log.e("=====", ""+id_pedido);

        if(db_pedido.eliminarPedido(id_pedido)){
            contenedor.setVisibility(View.GONE);
        }



    }

    public void setOnclickListeners(){
        eliminar.setOnClickListener(this);
    }


}


