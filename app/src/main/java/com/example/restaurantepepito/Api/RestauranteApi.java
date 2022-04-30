package com.example.restaurantepepito.Api;

import com.example.restaurantepepito.Model.RestauranteModel;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestauranteApi {


    //Cambiar IP
    public static final String BASE_URL = "http://192.168.10.55:8585/";



    @GET( "restaurante/listar_restaurante" )
    Call<List<RestauranteModel>> listar_restaurante ();



}
