package com.example.restaurantepepito.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.restaurantepepito.Adapter.Direccion_Adapter;
import com.example.restaurantepepito.Adapter.Plato_Adapter;
import com.example.restaurantepepito.Api.DireccionApi;
import com.example.restaurantepepito.Api.PlatoApi;
import com.example.restaurantepepito.Api.Usuario_DireccionAPI;
import com.example.restaurantepepito.Model.PlatoModel;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;
import com.example.restaurantepepito.NavigationHost;
import com.example.restaurantepepito.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Direccion_Lista_Fragment extends Fragment {

    FirebaseAuth mAuth;

    Button boton_añadir;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_direccion_lista, container, false);
        listar_direccion(view,mAuth.getUid());
        boton_añadir = view.findViewById(R.id.boton_lista_direccion_añadir_nueva_direccion);


        boton_añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new Direccion_Registrar_Fragment(),true);
            }
        });

        return view;
    }




    public void listar_direccion(View view,String id_usuario){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(DireccionApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        DireccionApi svc = retrofit.create(DireccionApi.class);

        Log.e("=========","============" + id_usuario);
        Call<List<Usuari_Direccion_Model>> lista_valores = svc.listar_direccion(id_usuario);
        lista_valores.enqueue(new Callback<List<Usuari_Direccion_Model>>() {


            @Override
            public void onResponse(Call<List<Usuari_Direccion_Model>> call, Response<List<Usuari_Direccion_Model>> response) {
                if(!response.isSuccessful()){
                    Log.e("CallService.onResponse","Error" + response.code());
                }
                else{

                    List<Usuari_Direccion_Model> lista = response.body();



                    RecyclerView recyclerView = view.findViewById(R.id.rv_direccion_lista_grid);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false));

                    Log.e("CallService.onResponse","Error");

                    Direccion_Adapter adapter = new Direccion_Adapter(lista);

                    recyclerView.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Call<List<Usuari_Direccion_Model>> call, Throwable t) {
                Log.e("CallService.onFailure", t.getLocalizedMessage());
            }
        });
    }

}