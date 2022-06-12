package com.example.restaurantepepito.Holder;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantepepito.Fragment.PlatoDetalle_Fragment;
import com.example.restaurantepepito.MainActivity;
import com.example.restaurantepepito.R;

public class Plato_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public Context context;

    public TextView tipo;
    public TextView nombre;
    public TextView precio;
    public Button boton_detalle;
    public String descripcion;
    public Integer id_plato;
    public ImageView imagen;
    public String imagen_url;


    public Plato_Holder(@NonNull View itemView) {
        super(itemView);
        itemView.getContext();

        context = itemView.getContext();




        tipo = itemView.findViewById(R.id.card_plato_tipo);
        nombre = itemView.findViewById(R.id.card_plato_nombre);
        precio = itemView.findViewById(R.id.card_plato_precio);
        boton_detalle = itemView.findViewById(R.id.card_plato_boton_detalle);
        imagen = itemView.findViewById(R.id.lista_imagen_plato);


    }

    public void setOnclickListeners(){
        boton_detalle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Double precio_m  = Double.parseDouble(precio.getText().toString().replace("S/",""));

        MainActivity myActivity = (MainActivity)context;
        Bundle datosAEnviar = new Bundle();
        datosAEnviar.putInt("id_plato", id_plato);
        datosAEnviar.putString("descripcion",descripcion);
        datosAEnviar.putString("nombre", nombre.getText().toString());
        datosAEnviar.putDouble("precio", precio_m);
        datosAEnviar.putString("imagen",imagen_url);


        Fragment fragmento = new PlatoDetalle_Fragment();

        fragmento.setArguments(datosAEnviar);

        FragmentManager fragmentManager = myActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_home, fragmento);
        fragmentTransaction.addToBackStack(null);


        fragmentTransaction.commit();

    }

}
