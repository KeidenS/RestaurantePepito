package com.example.restaurantepepito.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantepepito.Holder.Direccion_Holder;
import com.example.restaurantepepito.Holder.Plato_Holder;
import com.example.restaurantepepito.Model.PlatoModel;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;
import com.example.restaurantepepito.R;

import java.util.List;

public class Direccion_Adapter extends RecyclerView.Adapter<Direccion_Holder>{

    List<Usuari_Direccion_Model> direccion_lista;

    public Direccion_Adapter(List<Usuari_Direccion_Model> direccion_lista) {
        this.direccion_lista = direccion_lista;
    }

    @NonNull
    @Override
    public Direccion_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card_direccion, parent, false);
        return new Direccion_Holder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull Direccion_Holder holder, int position) {
        if (direccion_lista != null && position < direccion_lista.size()) {

            Usuari_Direccion_Model direccion_model = direccion_lista.get(position);



            String distrito= direccion_model.getDistrito();
            String localidad= direccion_model.getLocalidad();
            String direccion= direccion_model.getDireccion();
            String alias= direccion_model.getAlias();
            String lote= direccion_model.getLote();




            holder.nombre.setText(distrito+',' + ' ' + localidad+',' + ' ' + direccion);
            holder.alias.setText(alias);


        }
    }

    @Override
    public int getItemCount() {
        return direccion_lista.size();
    }

}
