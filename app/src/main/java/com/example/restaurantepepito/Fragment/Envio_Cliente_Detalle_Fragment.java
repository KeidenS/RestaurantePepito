package com.example.restaurantepepito.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.restaurantepepito.Api.EnvioApi;
import com.example.restaurantepepito.Api.Tipo_PedidoApi;
import com.example.restaurantepepito.MainActivity;
import com.example.restaurantepepito.Model.EnvioModel;
import com.example.restaurantepepito.Model.Tipo_PedidoModel;
import com.example.restaurantepepito.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Envio_Cliente_Detalle_Fragment extends Fragment {

    TextView envio_detalle_estado;
    TextView envio_detalle_nombre;
    TextView envio_detalle_apellido;

    Integer id_pedido;
    String restaurante_p;
    String direccion_p;
    EditText restaurante_envio;
    EditText direccion_envio;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle datosRecuperados = getArguments();
        if (datosRecuperados != null) {
            id_pedido = datosRecuperados.getInt("id_pedido_p");
            restaurante_p = datosRecuperados.getString("restaurante_p");
            direccion_p = datosRecuperados.getString("direccion_p");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_envio_cliente_detalle, container, false);
        envio_detalle_apellido = view.findViewById(R.id.envio_detalle_apellido);
        envio_detalle_nombre = view.findViewById(R.id.envio_detalle_nombre);
        envio_detalle_estado = view.findViewById(R.id.envio_detalle_estado);
        restaurante_envio = view.findViewById(R.id.restaurante_envio);
        direccion_envio = view.findViewById(R.id.direccion_envio);

        restaurante_envio.setText(restaurante_p);
        direccion_envio.setText(direccion_p);




        asignar_datos(id_pedido);

        return view;
    }

    public void asignar_datos(Integer id_ped){

        Retrofit retrofit = new Retrofit.Builder().baseUrl(EnvioApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        EnvioApi svc = retrofit.create(EnvioApi.class);


        Call<EnvioModel> valor = svc.listar_envio(id_ped);
        valor.enqueue(new Callback<EnvioModel>() {


            @Override
            public void onResponse(Call<EnvioModel> call, Response<EnvioModel> response) {

                EnvioModel model  = response.body();
                envio_detalle_nombre.setText(model.getNombre());
                envio_detalle_apellido.setText(model.getApellido());
                envio_detalle_estado.setText(model.getEstado());

            }

            @Override
            public void onFailure(Call<EnvioModel> call, Throwable t) {
                Log.e("CallService.onFailure", t.getLocalizedMessage());
            }
        });

    }

}