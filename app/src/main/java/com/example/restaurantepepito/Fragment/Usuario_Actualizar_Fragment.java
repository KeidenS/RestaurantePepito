package com.example.restaurantepepito.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurantepepito.Api.Usuario_DireccionAPI;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;
import com.example.restaurantepepito.NavigationHost;
import com.example.restaurantepepito.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Usuario_Actualizar_Fragment extends Fragment {

    private TextInputEditText nombre;
    private TextInputEditText apellido;
    private TextInputEditText celular;
    private Button mis_direcciones;
    private Button actualizar_usuario;
    FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuario_actualizar, container, false);


        nombre = view.findViewById(R.id.actualizar_usuario_nombre);
        apellido = view.findViewById(R.id.actualizar_usuario_apellido);
        celular = view.findViewById(R.id.actualizar_usuario_celular);
        actualizar_usuario = view.findViewById(R.id.boton_actualizar_usuario);
        mis_direcciones = view.findViewById(R.id.boton_actulizar_usuarios_midireccion);
        rellenar_datos(mAuth.getUid());


        actualizar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar_usuario(mAuth.getUid(),nombre.getText().toString(),apellido.getText().toString(),celular.getText().toString());
            }
        });

        mis_direcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new Direccion_Lista_Fragment(), true);
            }
        });

        return view;
    }


    private void actualizar_usuario(String id_usuario, String nombre, String apellido, String celular) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Usuario_DireccionAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Usuario_DireccionAPI svc = retrofit.create(Usuario_DireccionAPI.class);
        Call<Usuari_Direccion_Model> call = svc.actualizar_usuario(new Usuari_Direccion_Model(id_usuario, nombre, apellido, celular));
        call.enqueue(new Callback<Usuari_Direccion_Model>() {

            @Override
            public void onResponse(Call<Usuari_Direccion_Model> call, Response<Usuari_Direccion_Model> response) {
                ((NavigationHost) getActivity()).navigateTo(new Usuario_Actualizar_Fragment(), false);
            }

            @Override
            public void onFailure(Call<Usuari_Direccion_Model> call, Throwable t) {


            }
        });


    }

    private void rellenar_datos(String id_usuario){

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Usuario_DireccionAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Usuario_DireccionAPI svc = retrofit.create(Usuario_DireccionAPI.class);
        Call<Usuari_Direccion_Model> call = svc.buscar_usuario(id_usuario);
        call.enqueue(new Callback<Usuari_Direccion_Model>() {

            @Override
            public void onResponse(Call<Usuari_Direccion_Model> call, Response<Usuari_Direccion_Model> response) {
                Usuari_Direccion_Model user = response.body();
                nombre.setText(user.getNombre());
                apellido.setText(user.getApellido());
                celular.setText(user.getNumero_celular());
            }

            @Override
            public void onFailure(Call<Usuari_Direccion_Model> call, Throwable t) {


            }
        });





    }

}