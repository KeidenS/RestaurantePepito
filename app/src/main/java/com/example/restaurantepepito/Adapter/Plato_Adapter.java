package com.example.restaurantepepito.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantepepito.Holder.Plato_Holder;
import com.example.restaurantepepito.Model.PlatoModel;
import com.example.restaurantepepito.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class Plato_Adapter extends RecyclerView.Adapter<Plato_Holder>{

    List<PlatoModel> plato_lista;

    public Plato_Adapter(List<PlatoModel> plato_lista) {
        this.plato_lista = plato_lista;
    }

    @NonNull
    @Override
    public Plato_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card_plato_lista, parent, false);
        return new Plato_Holder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull Plato_Holder holder, int position) {
        if (plato_lista != null && position < plato_lista.size()) {

            PlatoModel plato_model = plato_lista.get(position);

            String precio = "S/"+String.format("%.2f",plato_model.getPrecio());
            String nombre= plato_model.getNombre();
            String tipo= plato_model.getTipo_plato();
            String descripcion= plato_model.getDescripcion();
            Integer idplato = plato_model.getIdplato();
            String imagen = plato_model.getImagen();

            holder.tipo.setText(tipo);
            holder.precio.setText(precio);
            holder.nombre.setText(nombre);
            holder.descripcion = descripcion;
            holder.id_plato = idplato;
            holder.imagen_url = imagen;
            Picasso.get().load(imagen).error(R.mipmap.ic_launcher).into(holder.imagen);

            holder.setOnclickListeners();
        }
    }

    @Override
    public int getItemCount() {
        return plato_lista.size();
    }

}
