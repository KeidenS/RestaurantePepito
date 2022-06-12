package com.example.restaurantepepito.Api;

import com.example.restaurantepepito.Model.EnvioModel;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EnvioApi {

    //Cambiar IP
    public static final String BASE_URL = "http://192.168.10.55:8585/";



    @GET( "envio/listar_envio/{id_pedido}" )
    Call<EnvioModel> listar_envio (@Path("id_pedido") Integer id_pedido);


}
