package com.example.restaurantepepito.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.restaurantepepito.Adapter.Direccion_Adapter;
import com.example.restaurantepepito.Api.CancelacionApi;
import com.example.restaurantepepito.Api.DireccionApi;
import com.example.restaurantepepito.MainActivity;
import com.example.restaurantepepito.Model.CancelacionModel;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;
import com.example.restaurantepepito.NavigationHost;
import com.example.restaurantepepito.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Cancelacion_Fragment extends Fragment {


    private EditText motivo;
    private Button cancelar;
    private Integer id_pedido;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle datosRecuperados = getArguments();
        if (datosRecuperados != null) {
            id_pedido = datosRecuperados.getInt("id_pedido_p");

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cancelacion, container, false);
        motivo = view.findViewById(R.id.motivo_cancelar);
        cancelar = view.findViewById(R.id.boton_cancelar);


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(motivo.getText().toString().equals("") || motivo.getText().toString().length() < 10){
                    ((NavigationHost) getActivity()).toastIncorrecto("Error, datos incorrectos");
                }
                else{

                    MainActivity myActivity = (MainActivity)getContext();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(myActivity);
                    alertDialogBuilder.setTitle("Cancelar Pedido");
                    alertDialogBuilder
                            .setMessage("¿Estas seguro que deseas cancelar el pedido? Ya no se podra revertir esta accion")
                            .setCancelable(false)
                            .setPositiveButton("Si",new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog,int id) {

                                    cancelar(motivo.getText().toString(),"Por confirmar",id_pedido);
                                    ((NavigationHost) getActivity()).navigateTo(new Fragment_Historial_Lista(),false);

                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            }).create().show();


                }
            }
        });

        return view;
    }

    private void cancelar(String mensaje,String estado,Integer id_ped){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(CancelacionApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        CancelacionApi svc = retrofit.create(CancelacionApi.class);


        Call<CancelacionModel> lista_valores = svc.registrar_cancelacion(new CancelacionModel(estado,mensaje,id_ped));
        lista_valores.enqueue(new Callback<CancelacionModel>() {


            @Override
            public void onResponse(Call<CancelacionModel> call, Response<CancelacionModel> response) {
                if(!response.isSuccessful()){
                    Log.e("CallService.onResponse","Error" + response.code());
                }
                else{



                }
            }

            @Override
            public void onFailure(Call<CancelacionModel> call, Throwable t) {
                Log.e("CallService.onFailure", t.getLocalizedMessage());
            }
        });
    }

}