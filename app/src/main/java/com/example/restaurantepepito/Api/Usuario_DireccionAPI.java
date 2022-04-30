package com.example.restaurantepepito.Api;

import com.example.restaurantepepito.Model.PlatoModel;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Usuario_DireccionAPI {

    //Cambiar IP
    public static final String BASE_URL = "http://192.168.10.55:8585/";



    @POST( "usuario/registrar_usuario" )
    Call<Usuari_Direccion_Model> registrar_usuario_direccion (@Body Usuari_Direccion_Model usuari_direccion_model);

    @PUT( "usuario/actualizar_usuario" )
    Call<Usuari_Direccion_Model> actualizar_usuario (@Body Usuari_Direccion_Model usuari_direccion_model);

    @GET( "usuario/buscar_usuario/{idusuario}" )
    Call<Usuari_Direccion_Model> buscar_usuario (@Path("idusuario") String idusuario);

    @POST( "usuario/registrar_direccion" )
    Call<Usuari_Direccion_Model> registrar_direccion (@Body Usuari_Direccion_Model usuari_direccion_model);

}
