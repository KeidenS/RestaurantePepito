package com.example.restaurantepepito.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.restaurantepepito.Adapter.CarritoListaAdapter;
import com.example.restaurantepepito.Api.DireccionApi;
import com.example.restaurantepepito.Api.RestauranteApi;
import com.example.restaurantepepito.MainActivity;
import com.example.restaurantepepito.Model.Pedido_x_PlatopModel;
import com.example.restaurantepepito.Model.RestauranteModel;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;
import com.example.restaurantepepito.NavigationHost;
import com.example.restaurantepepito.R;
import com.example.restaurantepepito.SQLite.DbPedido_x_Plato;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Pedido_Registrar_Fragment extends Fragment {



    private FirebaseAuth mAuth;
    private Spinner spinner_restaurante;
    private Spinner spinner_direccion;
    private Integer id_direccion;
    private Integer id_restaurante;
    private Button boton_fecha;
    private Button boton_hora;

    private TextView id_restaurante_t;
    private TextView id_direccion_t;
    private TextView fecha_t;
    private TextView hora_t;

    private Integer id_tipo_pedido;
    private String plazo_tipo_pedido;

    private TextView id_tipo;
    private TextView plazo;
    private Button pedido_ver_mapa;
    private Button pedido_registrar_direccion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        Bundle datosRecuperados = getArguments();
        if (datosRecuperados != null) {
            id_tipo_pedido = datosRecuperados.getInt("id_tipo_pedido");
            plazo_tipo_pedido = datosRecuperados.getString("plazo");

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pedido__registrar_, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_carrito_lista_grid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false));
        CarritoListaAdapter adapter = new CarritoListaAdapter(obtener_lista());
        recyclerView.setAdapter(adapter);


        spinner_restaurante = view.findViewById(R.id.carrito_spinner_restaurante);
        spinner_direccion = view.findViewById(R.id.carrito_spinner_direccion);
        boton_fecha = view.findViewById(R.id.carrito_boton_feha);
        boton_hora = view.findViewById(R.id.carrito_boton_hora);
        id_tipo = view.findViewById(R.id.carrito_id_tipo_pedido);
        plazo = view.findViewById(R.id.carrito_plazo_pedido);

        pedido_ver_mapa = view.findViewById(R.id.pedido_boton_ver_mapa);
        pedido_registrar_direccion = view.findViewById(R.id.pedido_boton_agregar_direccion);

        id_tipo.setText(id_tipo_pedido.toString());
        plazo.setText(plazo_tipo_pedido);

        id_restaurante_t = view.findViewById(R.id.id_restaurante_text);
        id_direccion_t = view.findViewById(R.id.id_direccion_text);
        fecha_t = view.findViewById(R.id.fecha);
        hora_t = view.findViewById(R.id.id_carrito_hora);

        listar_restaurante();
        listar_direccion();


        pedido_registrar_direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new Direccion_Registrar_Fragment(),true);
            }
        });

        pedido_ver_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        boton_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity myActivity = (MainActivity)getContext();
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(myActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Formatter fmtmes = new Formatter();
                        Formatter fmtdays = new Formatter();
                        int mess = month+1;
                        int days= dayOfMonth;
                        fmtmes.format("%02d",mess);
                        fmtdays.format("%02d",days);


                        String fecha= year + "-" + fmtmes + "-" + fmtdays;
                        //Asignar Fecha
                        fecha_t.setText(fecha);
                    }
                },anio,mes,dia);

                dpd.show();
            }
        });


        boton_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity myActivity = (MainActivity)getContext();
                Calendar cal = Calendar.getInstance();
                int hora = cal.get(Calendar.HOUR);
                int minutos = cal.get(Calendar.MINUTE);

                TimePickerDialog time = new TimePickerDialog(myActivity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        Calendar tempDate = Calendar.getInstance();
                        tempDate.set(Calendar.HOUR_OF_DAY,i);
                        tempDate.set(Calendar.MINUTE,i1);

                        SimpleDateFormat mSDF=new SimpleDateFormat("MM-dd-yyyy hh: mm a");

                        //Hora minima 9:00 A.M
                        Calendar dateTimeMin=Calendar.getInstance();
                        dateTimeMin.set(Calendar.HOUR_OF_DAY,9);
                        dateTimeMin.set(Calendar.MINUTE,0);

                        //Hora maxima 8:00 P.M
                        Calendar dateTimeMax=Calendar.getInstance();
                        dateTimeMax.set(Calendar.HOUR_OF_DAY,20);
                        dateTimeMax.set(Calendar.MINUTE,0);

                        //*Valida si la hora seleccionada es permitida.
                        if(tempDate.after(dateTimeMin)  && tempDate.before(dateTimeMax)){
                            Calendar datetime=Calendar.getInstance();
                            datetime.set(Calendar.HOUR_OF_DAY,i);
                            datetime.set(Calendar.MINUTE,i1);
                            String hora_seleccionada = i+":"+i1;
                            String hora_plazo = aumentar_minutos(i,i1,plazo_tipo_pedido);
                            hora_t.setText("De " + hora_seleccionada +" a " + hora_plazo );

                        } else {
                            Toast.makeText(getContext(), "Hora no permitida. Atendemos des las 9:00 AM hasta las 8:PM", Toast.LENGTH_SHORT).show();
                        }





                    }
                },hora,minutos,false);
                time.show();



            }
        });

        return view;
    }

    private String aumentar_minutos(int hora, int minutos,String  plazo){



        int plazo_hora = Integer.parseInt(plazo.substring(0,2));
        int plazo_minutos = Integer.parseInt(plazo.substring(3,5));
        int plazo_segundos= Integer.parseInt(plazo.substring(6,8));

        Calendar tiempo_actual = Calendar.getInstance();


        tiempo_actual.set(Calendar.MINUTE,minutos);
        tiempo_actual.set(Calendar.HOUR_OF_DAY,hora);
        Log.e("======",tiempo_actual.get(Calendar.MINUTE)+ "");
        Log.e("======",tiempo_actual.get(Calendar.HOUR_OF_DAY)+ "");


        tiempo_actual.add(Calendar.HOUR_OF_DAY,plazo_hora);
        tiempo_actual.add(Calendar.MINUTE,plazo_minutos);
        tiempo_actual.add(Calendar.SECOND,plazo_segundos);

        int hora_actual = tiempo_actual.get(Calendar.HOUR_OF_DAY);
        int minuto_actual = tiempo_actual.get(Calendar.MINUTE);
        String tiempo = hora_actual+":"+minuto_actual;


        return tiempo;
    }


    private void listar_restaurante(){



        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestauranteApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        RestauranteApi svc = retrofit.create(RestauranteApi.class);

        //CALL
        Call<List<RestauranteModel>> lista_valores = svc.listar_restaurante();
        lista_valores.enqueue(new Callback<List<RestauranteModel>>() {


            @Override
            public void onResponse(Call<List<RestauranteModel>> call, Response<List<RestauranteModel>> response) {
                if(!response.isSuccessful()){
                    Log.e("CallService.onResponse","Error" + response.code());
                }
                else{

                    List<RestauranteModel> restaurante = response.body();
                    cargar_spinners_restaurante(restaurante);
                }
            }

            @Override
            public void onFailure(Call<List<RestauranteModel>> call, Throwable t) {
                Log.e("CallService.onFailure", t.getLocalizedMessage());
            }
        });



    }

    private void listar_direccion(){
        String id_usuario = mAuth.getUid();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(DireccionApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        DireccionApi svc = retrofit.create(DireccionApi.class);

        //CALL
        Call<List<Usuari_Direccion_Model>> lista_valores = svc.listar_direccion(id_usuario);
        lista_valores.enqueue(new Callback<List<Usuari_Direccion_Model>>() {


            @Override
            public void onResponse(Call<List<Usuari_Direccion_Model>> call, Response<List<Usuari_Direccion_Model>> response) {
                if(!response.isSuccessful()){
                    Log.e("CallService.onResponse","Error" + response.code());
                }
                else{

                    List<Usuari_Direccion_Model> direccion = response.body();
                    cargar_spinners_direccion(direccion);
                }
            }

            @Override
            public void onFailure(Call<List<Usuari_Direccion_Model>> call, Throwable t) {
                Log.e("CallService.onFailure", t.getLocalizedMessage());
            }
        });


    }

    private void cargar_spinners_restaurante(List<RestauranteModel> restaurante){

        ArrayAdapter<RestauranteModel> restaurante_adapter= new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line,restaurante);
        spinner_restaurante.setAdapter(restaurante_adapter);
        spinner_restaurante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Integer id = restaurante.get(i).getIdrestaurante();
                //Asignar ID Restaurante
                id_restaurante_t.setText(id.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void cargar_spinners_direccion(List<Usuari_Direccion_Model> direccion){

        ArrayAdapter<Usuari_Direccion_Model> direccion_adapter= new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line,direccion);
        spinner_direccion.setAdapter(direccion_adapter);
        spinner_direccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Integer id = direccion.get(i).getIddireccion();
                //Asignar ID direccion
                id_direccion_t.setText(id.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public ArrayList<Pedido_x_PlatopModel> obtener_lista(){
        DbPedido_x_Plato db_pedido = new DbPedido_x_Plato(getContext());
        return db_pedido.mostrarPedidos();
    }

}