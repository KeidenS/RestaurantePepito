package com.example.restaurantepepito.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantepepito.Adapter.Plato_Adapter;
import com.example.restaurantepepito.Api.PlatoApi;
import com.example.restaurantepepito.Model.PlatoModel;
import com.example.restaurantepepito.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Plato_Lista_Fragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_plato_lista, container, false);
        listar_platos(view);

        return view;
    }

    public void listar_platos(View view){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(PlatoApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        PlatoApi svc = retrofit.create(PlatoApi.class);

        //CALL
        Call<List<PlatoModel>> lista_valores = svc.listar_plato();
        lista_valores.enqueue(new Callback<List<PlatoModel>>() {


            @Override
            public void onResponse(Call<List<PlatoModel>> call, Response<List<PlatoModel>> response) {
                if(!response.isSuccessful()){
                    Log.e("CallService.onResponse","Error" + response.code());
                }
                else{

                    List<PlatoModel> plato = response.body();



                    RecyclerView recyclerView = view.findViewById(R.id.rv_plato_lista_grid);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false));

                    Log.e("CallService.onResponse","Error");

                    Plato_Adapter adapter = new Plato_Adapter(plato);

                    recyclerView.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Call<List<PlatoModel>> call, Throwable t) {
                Log.e("CallService.onFailure", t.getLocalizedMessage());
            }
        });
    }

}