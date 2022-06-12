package com.example.restaurantepepito.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.restaurantepepito.Adapter.CarritoListaAdapter;
import com.example.restaurantepepito.Adapter.Pedido_Adapter;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Pedido_Registrar_Fragment extends Fragment {



    private FirebaseAuth mAuth;
    private EditText edit_text_restaurante;
    private Spinner spinner_direccion;
    private Integer id_direccion;
    private Integer id_restaurante;
    private Button boton_fecha;
    private Button boton_hora;

    public String hora_final_db;
    public String hora_inicial_db;
    public String fecha_db;

    private TextView id_restaurante_t;
    private TextView id_direccion_t;
    private TextView fecha_t;
    private TextView hora_t;
    private TextView monto_total;
    private TextView monto_envio;
    private TextView costo_envio_titulo;
    private TextView direccion_titulo;
    private TextView restaurante_titulo;
    private Integer id_tipo_pedido;
    private String plazo_tipo_pedido;

    private TextView id_tipo;
    private TextView plazo;
    private Button pedido_ver_mapa;
    private Button pedido_registrar_direccion;
    private Button pedido_registrar;



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

        Log.e("===========TIPO PEDIDO==",id_tipo_pedido.toString());

        rellenar_carrito(view);

        direccion_titulo = view.findViewById(R.id.direccion_text_titulo);
        restaurante_titulo = view.findViewById(R.id.restaurante_text_titulo);
        costo_envio_titulo = view.findViewById(R.id.costo_envio_text_titulo);

        edit_text_restaurante = view.findViewById(R.id.pedido_edittext_restaurante);
        spinner_direccion = view.findViewById(R.id.pedido_spinner_direccion);
        boton_fecha = view.findViewById(R.id.pedido_boton_feha);
        boton_hora = view.findViewById(R.id.pedido_boton_hora);
        id_tipo = view.findViewById(R.id.pedido_id_tipo_pedido);
        plazo = view.findViewById(R.id.pedido_plazo_pedido);

        pedido_ver_mapa = view.findViewById(R.id.pedido_boton_ver_mapa);
        pedido_registrar_direccion = view.findViewById(R.id.pedido_boton_agregar_direccion);

        id_tipo.setText(id_tipo_pedido.toString());
        plazo.setText("-El plazo para este servicio es de " + plazo_tipo_pedido.substring(3,5) + " minutos");

        id_restaurante_t = view.findViewById(R.id.id_pedido_restaurante);
        id_direccion_t = view.findViewById(R.id.id_pedido_direccion);
        fecha_t = view.findViewById(R.id.pedido_fecha);
        hora_t = view.findViewById(R.id.pedido_hora);
        pedido_registrar = view.findViewById(R.id.pedido_boton_realizar_pago);


        monto_total = view.findViewById(R.id.pedido_monto_total);
        monto_envio = view.findViewById(R.id.pedido_monto_envio);



        monto_envio.setText("0.0");

        listar_restaurante();
        listar_direccion();





        pedido_registrar_direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                        String fecha_editada = asignar_fecha_text_view(days,mess,anio);

                        fecha_t.setText(fecha_editada);

                        String fecha= year + "-" + fmtmes + "-" + fmtdays;
                        //Asignar Fecha
                        fecha_db= fecha;


                    }
                },anio,mes,dia);

                Date date =  new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_WEEK, 5);
                Date dateMod = calendar.getTime();

                Log.e("====",new Date().getTime() +"");
                Log.e("====",dateMod.getTime() +"");

                dpd.getDatePicker().setMinDate(new Date().getTime());
                dpd.getDatePicker().setMaxDate(dateMod.getTime());
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
                            hora_inicial_db = hora_seleccionada;
                            hora_final_db = hora_plazo;




                            hora_t.setText("De " + hora_seleccionada +" a " + hora_plazo );

                        } else {


                            ((NavigationHost) getActivity()).toastIncorrecto("Error, hora no permitida");

                        }





                    }
                },hora,minutos,true);

                time.show();



            }
        });

        pedido_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (fecha_t.getText().toString().equals("") || hora_t.getText().toString().equals("")) {
                    ((NavigationHost) getActivity()).toastIncorrecto("Error, rellene los datos");
                } else {

                    boolean validar = validar_hora_fecha_actual(fecha_db, hora_inicial_db);

                    if (validar == true) {

                        MainActivity myActivity = (MainActivity)getContext();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(myActivity);
                        alertDialogBuilder.setTitle("Registrar Pedido");
                        alertDialogBuilder
                                .setMessage("¿Deseas registrar el pedido y proceder a realizar el pago?")
                                .setCancelable(false)
                                .setPositiveButton("Si",new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog,int id) {

                                        Bundle datosAEnviar = new Bundle();

                                        Double monto = Double.parseDouble(monto_total.getText().toString().replace("S/", ""));
                                        Double envio = Double.parseDouble(monto_envio.getText().toString().replace("S/", ""));
                                        datosAEnviar.putString("hora_inicio", hora_inicial_db);
                                        datosAEnviar.putString("hora_final", hora_final_db);
                                        datosAEnviar.putString("fecha", fecha_db);

                                        datosAEnviar.putDouble("monto_envio_p", envio);
                                        datosAEnviar.putDouble("monto", monto);

                                        datosAEnviar.putInt("id_tipo", id_tipo_pedido);
                                        datosAEnviar.putInt("id_restaurante", Integer.parseInt(id_restaurante_t.getText().toString()));
                                        datosAEnviar.putInt("id_direccion", Integer.parseInt(id_direccion_t.getText().toString()));
                                        Fragment fragmento = new Pago_Fragment();

                                        fragmento.setArguments(datosAEnviar);

                                        FragmentManager fragmentManager = myActivity.getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.container_home, fragmento);
                                        fragmentTransaction.addToBackStack(null);


                                        fragmentTransaction.commit();


                                    }
                                })
                                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                }).create().show();







                    }


                }
            }




        });


        if(id_tipo_pedido == 1){
            spinner_direccion.setVisibility(View.INVISIBLE);
            pedido_registrar_direccion.setVisibility(View.GONE);
            direccion_titulo.setVisibility(View.GONE);
            costo_envio_titulo.setVisibility(View.GONE);
            monto_envio.setVisibility(View.GONE);


            String monto_i = "S/"+String.format("%.2f",cargar_monto());
            monto_total.setText(monto_i);


        }
        if(id_tipo_pedido==2){
            Double envio = 8.50;


            String monto_envio_i = "S/"+String.format("%.2f",envio);
            monto_envio.setText(monto_envio_i);


            String monto_i = "S/"+String.format("%.2f",cargar_monto()+envio);
            monto_total.setText(monto_i);

        }


        return view;



    }

    private String aumentar_minutos(int hora, int minutos,String  plazo){



        int plazo_hora = Integer.parseInt(plazo.substring(0,2));
        int plazo_minutos = Integer.parseInt(plazo.substring(3,5));
        int plazo_segundos= Integer.parseInt(plazo.substring(6,8));

        Calendar tiempo_actual = Calendar.getInstance();


        tiempo_actual.set(Calendar.MINUTE,minutos);
        tiempo_actual.set(Calendar.HOUR_OF_DAY,hora);


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
        Call<RestauranteModel> lista_valores = svc.listar_restaurante();
        lista_valores.enqueue(new Callback<RestauranteModel>() {


            @Override
            public void onResponse(Call<RestauranteModel> call, Response<RestauranteModel> response) {
                if(!response.isSuccessful()){
                    Log.e("CallService.onResponse","Error" + response.code());
                }
                else{

                    RestauranteModel restaurante = response.body();
                    id_restaurante_t.setText(restaurante.getIdrestaurante().toString());
                    edit_text_restaurante.setText(restaurante.getDistrito() +" "+  restaurante.getLocalidad());

                }
            }

            @Override
            public void onFailure(Call<RestauranteModel> call, Throwable t) {
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



    private void cargar_spinners_direccion(List<Usuari_Direccion_Model> direccion){

        ArrayAdapter<Usuari_Direccion_Model> direccion_adapter= new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line,direccion);
        spinner_direccion.setAdapter(direccion_adapter);
        spinner_direccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Integer id = direccion.get(i).getIddireccion();
                //Asignar ID direccion
                id_direccion_t.setText(id.toString());
                Log.e("=====ID DIRECCION", "===="+id_direccion_t.getText().toString());


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

    public double cargar_monto(){
        Double monto_total = 0.0;
        ArrayList<Pedido_x_PlatopModel> lista = obtener_lista();
        for (Pedido_x_PlatopModel p:lista) {
            monto_total = p.getSubtotal() + monto_total;

        }
        return monto_total;
    }

    public String asignar_fecha_text_view(int dia,int mes,int anio){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha = formato.parse(dia+"/"+mes+"/"+anio);
            Locale idioma = Locale.getDefault();
            String diaYMes = (new SimpleDateFormat("EEEE dd 'de' MMMM", idioma)).format(fecha);
            char[] caracteres = diaYMes.toCharArray();
            caracteres[0] = Character.toUpperCase(caracteres[0]);
            for (int i = 0; i < diaYMes.length()- 2; i++){
                if (caracteres[i] == ' ' || caracteres[i] == '.' || caracteres[i] == ','){
                    caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]);
                }
            }
            String fecha_convert = new String(caracteres);


            return fecha_convert;
        } catch (ParseException e) {
            e.printStackTrace();
            return "error";
        }



    }

    public void rellenar_carrito(View view){
        RecyclerView recyclerView = view.findViewById(R.id.rv_carrito_pedido_lista_grid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false));
        Pedido_Adapter adapter = new Pedido_Adapter(obtener_lista());
        recyclerView.setAdapter(adapter);
    }



    public boolean  validar_hora_fecha_actual(String fecha, String hora_inicial){

        Boolean validar;
        String fecha_actual = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        String hora_actual = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());

        Date fecha1, fecha2;

        fecha1 = new Date(0,0,0,Integer.parseInt(hora_actual.substring(0,2)),Integer.parseInt(hora_actual.substring(3,5)),0);

        fecha2 = new Date(0,0,0,Integer.parseInt(hora_inicial.substring(0,2)),Integer.parseInt(hora_inicial.substring(3,5)),0);






        if(fecha_actual.equals(fecha)){

            if(hora_actual.equals(hora_inicial) || fecha2.before(fecha1)){

                ((NavigationHost) getActivity()).toastIncorrecto("Error, hora ioncorrecta");
                validar = false;
            }else{
                validar = true;
            }

        }
        else{

            validar = true;


        }


        return validar;


    }



}