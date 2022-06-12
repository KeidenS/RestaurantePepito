package com.example.restaurantepepito.Api;

import com.example.restaurantepepito.Model.PagoModel;
import com.example.restaurantepepito.Model.Resultado_Pago;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PagoApi {


    //Cambiar IP
    public static final String BASE_URL = "http://192.168.10.55:8585/";



    @POST( "cargo/registrar_cargo_prueba" )
    Call<Resultado_Pago> registrar_pago (@Body PagoModel pago_model);

}
