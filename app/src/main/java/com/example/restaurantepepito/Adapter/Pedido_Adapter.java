package com.example.restaurantepepito.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantepepito.Custom.Botones;
import com.example.restaurantepepito.Custom.Parametros;
import com.example.restaurantepepito.Holder.CarritoListaHolder;
import com.example.restaurantepepito.Holder.Direccion_Holder;
import com.example.restaurantepepito.Model.Pedido_x_PlatopModel;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;
import com.example.restaurantepepito.R;

import java.util.ArrayList;
import java.util.List;

public class Pedido_Adapter extends RecyclerView.Adapter<CarritoListaHolder>{

    List<Pedido_x_PlatopModel> carrito;

    public Pedido_Adapter(List<Pedido_x_PlatopModel> carrito) {
        this.carrito = carrito;
    }

    @NonNull
    @Override
    public CarritoListaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.carrito_pedido_lista_card, parent, false);
        return new CarritoListaHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoListaHolder holder, int position) {
        if (carrito != null && position < carrito.size()) {

            Pedido_x_PlatopModel pedido_model = carrito.get(position);


            String nombre= pedido_model.getNombre();
            String precio = "S/"+String.format("%.2f",pedido_model.getPrecio());
            String cantidad = Integer.toString(pedido_model.getCantidad());
            String subtotal = "S/"+String.format("%.2f",pedido_model.getSubtotal());





            holder.nombre.setText(nombre);
            holder.precio.setText(precio);
            holder.cantidad.setText(cantidad);
            holder.subtotal.setText(subtotal);
            holder.setId_pedido(pedido_model.getId_pedido());
            holder.setId_plato(pedido_model.getId_plato());



        }
    }

    @Override
    public int getItemCount() {
        return carrito.size();
    }

}
