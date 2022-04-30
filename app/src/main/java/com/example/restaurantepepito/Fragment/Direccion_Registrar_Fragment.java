package com.example.restaurantepepito.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.restaurantepepito.Api.Usuario_DireccionAPI;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;
import com.example.restaurantepepito.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Direccion_Registrar_Fragment extends Fragment {

    private GoogleMap mMap;

    private Marker marcador;
    private Marker currentMarker;
    private TextInputEditText distrito;
    private TextInputEditText localidad;
    private TextInputEditText direccion;
    private TextInputEditText alias;
    private TextInputEditText lote;
    private TextView latitud_registrar;
    private TextView longitud_registrar;
    private Button mi_direccion;
    private Button registrar_direccion;
    FirebaseAuth mAuth;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_direccion_registrar, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map2);

        distrito = view.findViewById(R.id.registrar_direccion_distrito);
        localidad = view.findViewById(R.id.registrar_direccion_localidad);
        direccion = view.findViewById(R.id.registrar_direccion_direccion);
        alias = view.findViewById(R.id.registrar_direccion_alias);
        lote = view.findViewById(R.id.registrar_direccion_lote);
        latitud_registrar = view.findViewById(R.id.registrar_direccion_latitud);
        longitud_registrar = view.findViewById(R.id.registrar_direccion_longitud);
        mi_direccion = view.findViewById(R.id.boton_direccion_midireccion_registrar);
        registrar_direccion = view.findViewById(R.id.boton_direccion_registrar);





        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                //--------------


                mMap = googleMap;


                LatLng lima = new LatLng(-12.045099033676845, -77.04291264078462);
                mMap.addMarker(new MarkerOptions().position(lima).title("Marcador en Lima"));


                mMap.setMaxZoomPreference(20);


                mMap.setMinZoomPreference(10);


                mMap.getUiSettings().setZoomControlsEnabled(true);


                mMap.moveCamera(CameraUpdateFactory.newLatLng(lima));


                currentMarker = null;
                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(@NonNull LatLng latLng) {
                        LatLng mi_ubicacion = new LatLng(latLng.latitude, latLng.longitude);

                        if (currentMarker != null) {
                            currentMarker.remove();
                            currentMarker = null;
                        } else {
                            currentMarker = mMap.addMarker(new MarkerOptions().position(mi_ubicacion).title("Mi ubicacion"));


                            asignar_nombres_direcciones(latLng.latitude, latLng.longitude);
                            ubicarCamara(latLng.latitude, latLng.longitude);


                        }

                    }
                });

            }
        });

        mi_direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location mi_ubicacion = miUbicacion();
                asignar_nombres_direcciones(mi_ubicacion.getLatitude(),miUbicacion().getLongitude());
                ubicarCamara(mi_ubicacion.getLatitude(),mi_ubicacion.getLongitude());
            }
        });

        registrar_direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar_direccion(mAuth.getUid(),Double.parseDouble(longitud_registrar.getText().toString()),Double.parseDouble(latitud_registrar.getText().toString()),distrito.getText().toString(),direccion.getText().toString(),localidad.getText().toString(),alias.getText().toString(),lote.getText().toString());


            }
        });

        return view;
    }

    private void ubicarCamara(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 18);

        mMap.animateCamera(miUbicacion);
    }

    private void asignar_nombres_direcciones(Double latitud,Double longitud){
        Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {

            addresses = gcd.getFromLocation(latitud, longitud,1);
            String direccion_cortar =  ""+addresses.get(0).getAddressLine(0);
            String[] parts = direccion_cortar.split(",");
            direccion.setText(parts[0]);
            distrito.setText(addresses.get(0).getLocality());
            localidad.setText(addresses.get(0).getSubLocality());
            latitud_registrar.setText(latitud.toString());
            longitud_registrar.setText(longitud.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Location miUbicacion() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        LocationManager locationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return location;

    }

    private void registrar_direccion(String id_usuario,Double longitud,Double latitud,String distrito,String direccion,String localidad,String alias,String lote){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Usuario_DireccionAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Usuario_DireccionAPI svc = retrofit.create(Usuario_DireccionAPI.class);
        Call<Usuari_Direccion_Model> call = svc.registrar_direccion(new Usuari_Direccion_Model(id_usuario,longitud,latitud,distrito,direccion,localidad,alias,lote));
        call.enqueue(new Callback<Usuari_Direccion_Model>() {

            @Override
            public void onResponse(Call<Usuari_Direccion_Model> call, Response<Usuari_Direccion_Model> response) {

            }

            @Override
            public void onFailure(Call<Usuari_Direccion_Model> call, Throwable t) {


            }
        });
    }




}