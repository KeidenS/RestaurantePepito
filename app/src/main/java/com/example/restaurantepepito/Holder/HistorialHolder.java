package com.example.restaurantepepito.Holder;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantepepito.Api.Tipo_PedidoApi;
import com.example.restaurantepepito.Fragment.Detalle_Pedido_Fragment;
import com.example.restaurantepepito.Fragment.Pedido_Registrar_Fragment;
import com.example.restaurantepepito.Fragment.PlatoDetalle_Fragment;
import com.example.restaurantepepito.MainActivity;
import com.example.restaurantepepito.Model.Tipo_PedidoModel;
import com.example.restaurantepepito.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistorialHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public Context context;

    public TextView nombre;
    public TextView fecha;
    public TextView total;
    public TextView estado;
    public Button detalle;

    public String hora_inicio_p;
    public String hora_final_p;
    public String fecha_p;
    public Integer id_p;
    public Integer id_tipo_pedido_p;
    public String plazo_p;
    public Integer id_restaurante;
    public Integer id_direccion;
    public Double envio_costo;
    public ImageView imagen;
    public String estado_p;
    public String fecha_pedido_p;



    public HistorialHolder(@NonNull View itemView) {
        super(itemView);
        itemView.getContext();

        context = itemView.getContext();




        nombre = itemView.findViewById(R.id.card_hitorial_nombre);
        fecha = itemView.findViewById(R.id.card_historial_fecha);
        total = itemView.findViewById(R.id.card_historial_total);
        estado = itemView.findViewById(R.id.card_historial_estado);
        detalle = itemView.findViewById(R.id.card_historial_boton_detalle);
        imagen = itemView.findViewById(R.id.imagen_estado);


    }

    public void setOnclickListeners(){
        detalle.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        MainActivity myActivity = (MainActivity)context;
        Bundle datosAEnviar = new Bundle();
        datosAEnviar.putString("hora_inicio_p", hora_inicio_p);
        datosAEnviar.putString("hora_final_p", hora_final_p);

        Double total_m= Double.parseDouble(total.getText().toString().replace("S/",""));

        datosAEnviar.putDouble("costo_p",total_m);
        datosAEnviar.putDouble("envio_costo_p",envio_costo);
        datosAEnviar.putString("fecha_p", fecha_p);
        datosAEnviar.putInt("id_p", id_p);
        datosAEnviar.putInt("id_tipo_pedido_p", id_tipo_pedido_p);
        datosAEnviar.putInt("id_restaurante", id_restaurante);
        datosAEnviar.putInt("id_direccion", id_direccion);
        datosAEnviar.putString("plazo_p", plazo_p);
        datosAEnviar.putString("estado_p", estado_p);
        datosAEnviar.putString("fecha_pedido_p", fecha_pedido_p);



        Fragment fragmento = new Detalle_Pedido_Fragment();

        fragmento.setArguments(datosAEnviar);

        FragmentManager fragmentManager = myActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_home, fragmento);
        fragmentTransaction.addToBackStack(null);


        fragmentTransaction.commit();


    }




}