package com.example.restaurantepepito.Api;

import com.example.restaurantepepito.Model.CancelacionModel;
import com.example.restaurantepepito.Model.PedidoModel;
import com.example.restaurantepepito.Model.Pedido_x_PlatopModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CancelacionApi {
    public static final String BASE_URL = "http://192.168.10.55:8585/";

    @POST( "cancelar/registrar_cancelacion" )
    Call<CancelacionModel> registrar_cancelacion (@Body CancelacionModel cancelacion);

}
