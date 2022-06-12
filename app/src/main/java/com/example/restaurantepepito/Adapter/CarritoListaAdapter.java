package com.example.restaurantepepito.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantepepito.Custom.Botones;
import com.example.restaurantepepito.Custom.Parametros;
import com.example.restaurantepepito.Holder.CarritoListaHolder;
import com.example.restaurantepepito.MainActivity;
import com.example.restaurantepepito.Model.Pedido_x_PlatopModel;
import com.example.restaurantepepito.R;
import com.example.restaurantepepito.SQLite.DbPedido_x_Plato;

import java.util.ArrayList;

public class CarritoListaAdapter extends RecyclerView.Adapter<CarritoListaAdapter.ViewHolderDatos> implements Botones {

    ArrayList<Pedido_x_PlatopModel> pedido_lista;
    Context context;
    private Parametros parametros;

    public void setParametros(Parametros parametros) {
        this.parametros = parametros;
    }

    public CarritoListaAdapter(ArrayList<Pedido_x_PlatopModel> pedido_lista, Parametros parametros) {
        this.pedido_lista = pedido_lista;
        this.parametros = parametros;

    }

    @NonNull
    @Override
    public CarritoListaAdapter.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.carrito_lista_card, parent, false);



        return new ViewHolderDatos(layoutView,this);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {

        if (pedido_lista != null && position < pedido_lista.size()) {

            Pedido_x_PlatopModel pedidoModel = pedido_lista.get(position);


            String nombre= pedidoModel.getNombre();
            String precio = "S/"+ String.format("%.2f",pedidoModel.getPrecio());
            String cantidad = Integer.toString(pedidoModel.getCantidad());
            String subtotal = "S/"+ String.format("%.2f",pedidoModel.getSubtotal());




            holder.nombre.setText(nombre);
            holder.precio.setText(precio);
            holder.cantidad.setText(cantidad);
            holder.subtotal.setText(subtotal);
            holder.setId_pedido(pedidoModel.getId_pedido());
            holder.setId_plato(pedidoModel.getId_plato());








        }

    }


    @Override
    public int getItemCount() {
        return pedido_lista.size();
    }


    @Override
    public void onEliminarClicked(int position) {

        parametros.onParametrosClicked(position,pedido_lista.get(position).getId_pedido());

    }


    public class ViewHolderDatos extends RecyclerView.ViewHolder {



        public TextView nombre;
        public TextView precio;
        public TextView cantidad;
        public TextView subtotal;
        public Button eliminar;
        public CardView contenedor;

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

        public ViewHolderDatos(@NonNull View itemView, Botones botones) {
            super(itemView);




            contenedor  = itemView.findViewById(R.id.carrito_contenedor);
            nombre = itemView.findViewById(R.id.card_carrito_nombre);
            precio = itemView.findViewById(R.id.card_carrito_precio);
            cantidad = itemView.findViewById(R.id.card_carrito_cantidad);
            subtotal = itemView.findViewById(R.id.card_carrito_subtotal);
            eliminar = itemView.findViewById(R.id.card_carrito_boton_eliminar);




            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    botones.onEliminarClicked(position);
                    contenedor.setVisibility(View.GONE);
                }
            });

        }





    }
}
