package com.example.restaurantepepito.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.restaurantepepito.Adapter.CarritoListaAdapter;
import com.example.restaurantepepito.Adapter.Direccion_Adapter;
import com.example.restaurantepepito.Adapter.Plato_Adapter;
import com.example.restaurantepepito.Api.DireccionApi;
import com.example.restaurantepepito.Api.PlatoApi;
import com.example.restaurantepepito.Api.RestauranteApi;
import com.example.restaurantepepito.Api.Tipo_PedidoApi;
import com.example.restaurantepepito.MainActivity;
import com.example.restaurantepepito.Model.Pedido_x_PlatopModel;
import com.example.restaurantepepito.Model.PlatoModel;
import com.example.restaurantepepito.Model.RestauranteModel;
import com.example.restaurantepepito.Model.Tipo_PedidoModel;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;
import com.example.restaurantepepito.R;
import com.example.restaurantepepito.SQLite.DbPedido_x_Plato;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Carrito_Fragment extends Fragment {


    private Button boton_recoger;
    private Button boton_delivery;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito_, container, false);


        boton_recoger = view.findViewById(R.id.carrito_opcion_recoger);
        boton_delivery = view.findViewById(R.id.carrito_opcion_delivery);



        boton_recoger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                tipo_pedido(1);

            }
        });

        boton_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tipo_pedido(2);

            }
        });

        tipo_pedido(1);

        return view;
    }




    public void tipo_pedido(Integer id){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Tipo_PedidoApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        Tipo_PedidoApi svc = retrofit.create(Tipo_PedidoApi.class);


        Call<Tipo_PedidoModel> valor = svc.buscar_tipo(id);
        valor.enqueue(new Callback<Tipo_PedidoModel>() {


            @Override
            public void onResponse(Call<Tipo_PedidoModel> call, Response<Tipo_PedidoModel> response) {
                if(!response.isSuccessful()){
                    Log.e("CallService.onResponse","Error" + response.code());
                }
                else{

                    Tipo_PedidoModel valor = response.body();


                    MainActivity myActivity = (MainActivity)getContext();

                    Bundle datosAEnviar = new Bundle();
                    datosAEnviar.putInt("id_tipo_pedido", valor.getIdtipo_pedido());
                    datosAEnviar.putString("plazo",valor.getPlazo());



                    Fragment fragmento = new Pedido_Registrar_Fragment();
                    fragmento.setArguments(datosAEnviar);

                    FragmentManager fragmentManager = myActivity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_carrito, fragmento);
                    fragmentTransaction.addToBackStack(null);


                    fragmentTransaction.commit();

                }
            }

            @Override
            public void onFailure(Call<Tipo_PedidoModel> call, Throwable t) {
                Log.e("CallService.onFailure", t.getLocalizedMessage());
            }
        });
    }







}