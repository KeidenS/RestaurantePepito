package com.example.restaurantepepito.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantepepito.Holder.CarritoListaHolder;
import com.example.restaurantepepito.Model.Pedido_x_PlatopModel;
import com.example.restaurantepepito.R;

import java.util.ArrayList;

public class CarritoListaAdapter extends RecyclerView.Adapter<CarritoListaHolder>{

    ArrayList<Pedido_x_PlatopModel> pedido_lista;

    public CarritoListaAdapter(ArrayList<Pedido_x_PlatopModel> pedido_lista) {
        this.pedido_lista = pedido_lista;
    }

    @NonNull
    @Override
    public CarritoListaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.carrito_lista_card, parent, false);
        return new CarritoListaHolder(layoutView);

    }

    @Override
    public void onBindViewHolder(@NonNull CarritoListaHolder holder, int position) {

        if (pedido_lista != null && position < pedido_lista.size()) {

            Pedido_x_PlatopModel pedidoModel = pedido_lista.get(position);


            String nombre= pedidoModel.getNombre();
            String precio = Double.toString(pedidoModel.getPrecio());
            String cantidad = Integer.toString(pedidoModel.getCantidad());
            String subtotal = Double.toString(pedidoModel.getSubtotal());


            holder.nombre.setText(nombre);
            holder.precio.setText(precio);
            holder.cantidad.setText(cantidad);
            holder.subtotal.setText(subtotal);
            holder.setId_pedido(pedidoModel.getId_pedido());
            holder.setId_plato(pedidoModel.getId_plato());


            holder.setOnclickListeners();
        }

    }

    @Override
    public int getItemCount() {
        return pedido_lista.size();
    }
}
