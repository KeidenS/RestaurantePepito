package com.example.restaurantepepito.Api;

import com.example.restaurantepepito.Model.PlatoModel;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PlatoApi {

    //Cambiar IP
    public static final String BASE_URL = "http://192.168.10.55:8585/";



    @GET( "plato/listar_plato" )
    Call<List<PlatoModel>> listar_plato ();


}
