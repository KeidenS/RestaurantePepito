package com.example.restaurantepepito.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurantepepito.Api.Usuario_DireccionAPI;
import com.example.restaurantepepito.LoginActivity;
import com.example.restaurantepepito.MainActivity;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;
import com.example.restaurantepepito.NavigationHost;
import com.example.restaurantepepito.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Registrar_UsuarioDireccion_Fragment extends Fragment {



    Context context;

    private TextInputEditText nombre;
    private TextInputEditText apellido;
    private TextInputEditText celular;
    private TextInputEditText correo;
    private TextInputEditText contraseña;



    private Button continuar_registrar_usuario_direccion;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registrar__usuario_direccion_, container, false);


        nombre = view.findViewById(R.id.registrar_usuario_nombre);
        apellido = view.findViewById(R.id.registrar_usuario_apellido);
        celular = view.findViewById(R.id.registrar_usuario_celular);
        correo = view.findViewById(R.id.registrar_usuario_correo);
        contraseña = view.findViewById(R.id.registrar_usuario_contraseña);
        continuar_registrar_usuario_direccion = view.findViewById(R.id.boton_continar_registrar_usuario_direccion);



        continuar_registrar_usuario_direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = correo.getText().toString();
                String password = contraseña.getText().toString();
                String nombres = nombre.getText().toString();
                String apellidos = apellido.getText().toString();
                String celulars = celular.getText().toString();



                Bundle datosAEnviar = new Bundle();
                datosAEnviar.putString("email_b", email);
                datosAEnviar.putString("password_b",password);
                datosAEnviar.putString("nombres_b",nombres);
                datosAEnviar.putString("apellidos_b",apellidos);
                datosAEnviar.putString("celulars_b",celulars);


                Fragment fragmento = new Registrar_DireccionUsuario_Fragment();

                fragmento.setArguments(datosAEnviar);

                ((NavigationHost) getActivity()).navigateTo(fragmento,true);



            }
        });

        return view;
    }






}