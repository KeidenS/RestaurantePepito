package com.example.restaurantepepito.Api;

import com.example.restaurantepepito.Model.PagoModel;
import com.example.restaurantepepito.Model.PedidoModel;
import com.example.restaurantepepito.Model.Pedido_x_PlatopModel;
import com.example.restaurantepepito.Model.Tipo_PedidoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PedidoApi {

    //Cambiar IP
    public static final String BASE_URL = "http://192.168.10.55:8585/";

    @GET( "pedido/listar_pedido/{idusuario}" )
    Call<List<PedidoModel>> listar_pedido (@Path("idusuario") String idusuario);

    @GET( "pedido/listar_pedido_x_plato/{idplato}" )
    Call<List<Pedido_x_PlatopModel>> listar_pedido_x_plato (@Path("idplato") Integer idusuario);

    @POST( "pedido/registrar_pedido" )
    Call<Integer> registrar_pedido (@Body PedidoModel pedidoModel);

    @PUT( "pedido/actualizar_pedido" )
    Call<PedidoModel> actualizar_pedido (@Body PedidoModel pedidoModel);

    @POST( "pedido/registrar_pedido_x_plato" )
    Call<Pedido_x_PlatopModel> registrar_pedido_x_plato (@Body Pedido_x_PlatopModel pedido_x_platopModel);

}
