package com.example.restaurantepepito.Holder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantepepito.R;

public class Direccion_Holder extends RecyclerView.ViewHolder {

    public Context context;

    public TextView nombre;
    public TextView alias;



    public Direccion_Holder(@NonNull View itemView) {
        super(itemView);
        itemView.getContext();

        context = itemView.getContext();




        alias = itemView.findViewById(R.id.card_direccion_alias);
        nombre = itemView.findViewById(R.id.card_direccion_nombre);



    }

}
