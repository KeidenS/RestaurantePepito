package com.example.restaurantepepito.Api;

import com.example.restaurantepepito.Model.Tipo_PedidoModel;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Tipo_PedidoApi {
    //Cambiar IP
    public static final String BASE_URL = "http://192.168.10.55:8585/";



    @GET( "tipo_pedido/buscar_tipo_pedido/{idtipo}" )
    Call<Tipo_PedidoModel> buscar_tipo (@Path("idtipo") Integer idtipo);
}
