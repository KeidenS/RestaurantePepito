package com.example.restaurantepepito.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.restaurantepepito.Adapter.Pedido_Adapter;
import com.example.restaurantepepito.Adapter.Plato_Adapter;
import com.example.restaurantepepito.Api.DireccionApi;
import com.example.restaurantepepito.Api.DocumentoPagoApi;
import com.example.restaurantepepito.Api.PedidoApi;
import com.example.restaurantepepito.Api.PlatoApi;
import com.example.restaurantepepito.Api.RestauranteApi;
import com.example.restaurantepepito.Api.Tipo_PedidoApi;
import com.example.restaurantepepito.Custom.PdfComprobante;
import com.example.restaurantepepito.MainActivity;
import com.example.restaurantepepito.Model.DocumentoModel;
import com.example.restaurantepepito.Model.PedidoModel;
import com.example.restaurantepepito.Model.Pedido_x_PlatopModel;
import com.example.restaurantepepito.Model.PlatoModel;
import com.example.restaurantepepito.Model.RestauranteModel;
import com.example.restaurantepepito.Model.Tipo_PedidoModel;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;
import com.example.restaurantepepito.NavigationHost;
import com.example.restaurantepepito.R;
import com.example.restaurantepepito.SQLite.DbPedido_x_Plato;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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


public class Detalle_Pedido_Fragment extends Fragment {



    private EditText detalle_edit_text_restaurante;
    private Button detalle_pedido_ver_mapa;
    private Spinner detalle_spinner_direccion;
    private Button detalle_pedido_boton_agregar_direccion;
    private TextView pedido_plazo_pedido;
    private TextView detalle_pedido_hora;
    private Button detalle_pedido_boton_hora;
    private TextView detalle_pedido_id_restaurante;
    private TextView detalle_pedido_id_direccion;
    private TextView detalle_pedido_fecha;
    private Button detalle_pedido_boton_feha;
    private TextView detalle_pedido_monto_envio;
    private TextView detalle_pedido_monto_total;

    private Button detalle_pedido_boton_actualizar;
    private Button detalle_pedido_boton_eliminar;
    private Button detalle_pedido_informacion_envio;

    private TextView mensaje_envio;
    private TextView mensaje_direccion;
    private Button documento;

    private Integer id_p;
    private Integer id_tipo_pedido;
    private Double costo_p;
    private Double envio_costo_p;
    private String hora_inicio_p;
    private String hora_final_p;
    private String fecha_p;
    private String plazo_pedido_p;
    private String estado_p;
    private String fecha_pedido_p;
    public Integer id_restaurante_p;
    public Integer id_direccion_p;
    public String direccion_nombre;
    FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        Bundle datosRecuperados = getArguments();
        if (datosRecuperados != null) {
            id_p = datosRecuperados.getInt("id_p");
            id_restaurante_p = datosRecuperados.getInt("id_restaurante");
            id_direccion_p = datosRecuperados.getInt("id_direccion");
            id_tipo_pedido = datosRecuperados.getInt("id_tipo_pedido_p");
            hora_inicio_p = datosRecuperados.getString("hora_inicio_p");
            hora_final_p = datosRecuperados.getString("hora_final_p");
            fecha_p = datosRecuperados.getString("fecha_p");
            estado_p = datosRecuperados.getString("estado_p");
            fecha_pedido_p = datosRecuperados.getString("fecha_pedido_p");
            plazo_pedido_p= datosRecuperados.getString("plazo_p");
            costo_p = datosRecuperados.getDouble("costo_p");
            envio_costo_p = datosRecuperados.getDouble("envio_costo_p");

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalle__pedido_, container, false);



        mensaje_envio = view.findViewById(R.id.detalle_costo_envio_text_titulo);
        mensaje_direccion = view.findViewById(R.id.detalle_direccion_text_titulo);

        detalle_edit_text_restaurante= view.findViewById(R.id.detalle_pedido_spinner_restaurante);
        detalle_pedido_ver_mapa= view.findViewById(R.id.detalle_pedido_boton_ver_mapa);
        detalle_spinner_direccion= view.findViewById(R.id.detalle_pedido_spinner_direccion);
        detalle_pedido_boton_agregar_direccion= view.findViewById(R.id.detalle_pedido_boton_agregar_direccion);
        pedido_plazo_pedido= view.findViewById(R.id.pedido_plazo_pedido);
        detalle_pedido_hora= view.findViewById(R.id.detalle_pedido_hora);
        detalle_pedido_boton_hora= view.findViewById(R.id.detalle_pedido_boton_hora);
        detalle_pedido_id_restaurante= view.findViewById(R.id.detalle_pedido_id_restaurante);
        detalle_pedido_id_direccion= view.findViewById(R.id.detalle_pedido_id_direccion);
        detalle_pedido_fecha= view.findViewById(R.id.detalle_pedido_fecha);
        documento = view.findViewById(R.id.boton_detalle_pedido_documento);
        detalle_pedido_boton_feha= view.findViewById(R.id.detalle_pedido_boton_feha);
        detalle_pedido_monto_envio= view.findViewById(R.id.detalle_pedido_monto_envio);
        detalle_pedido_monto_total= view.findViewById(R.id.detalle_pedido_monto_total);
        detalle_pedido_boton_actualizar= view.findViewById(R.id.detalle_pedido_boton_actualizar);
        detalle_pedido_boton_eliminar= view.findViewById(R.id.detalle_pedido_boton_eliminar);
        detalle_pedido_informacion_envio = view.findViewById(R.id.detalle_pedido_informacion_envio);

        asignar_hora_text_view();
        asignar_fecha_text_view();

        String precio = "S/"+String.format("%.2f",costo_p);
        detalle_pedido_monto_total.setText(precio);



        pedido_plazo_pedido.setText("-El plazo para este servicio es de " + plazo_pedido_p.substring(3,5) + " minutos");
        listar_direccion();
        listar_restaurante();


        String precio_envio = "S/"+String.format("%.2f",envio_costo_p);
        detalle_pedido_monto_envio.setText(precio_envio);
        listar_platos(view,id_p);




        documento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl(DocumentoPagoApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

                DocumentoPagoApi svc = retrofit.create(DocumentoPagoApi.class);

                //CALL
                Call<DocumentoModel> lista_valores = svc.listar_documento(id_p);
                lista_valores.enqueue(new Callback<DocumentoModel>() {


                    @Override
                    public void onResponse(Call<DocumentoModel> call, Response<DocumentoModel> response) {
                        if(!response.isSuccessful()){
                            Log.e("CallService.onResponse","Error" + response.code());
                        }
                        else{

                            DocumentoModel documento = response.body();


                            Retrofit retrofit = new Retrofit.Builder().baseUrl(PlatoApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

                            PedidoApi svc = retrofit.create(PedidoApi.class);

                            //CALL
                            Call<List<Pedido_x_PlatopModel>> lista_valores = svc.listar_pedido_x_plato(id_p);
                            lista_valores.enqueue(new Callback<List<Pedido_x_PlatopModel>>() {


                                @Override
                                public void onResponse(Call<List<Pedido_x_PlatopModel>> call, Response<List<Pedido_x_PlatopModel>> response) {
                                    if(!response.isSuccessful()){
                                        Log.e("CallService.onResponse","Error" + response.code());
                                    }
                                    else{

                                        List<Pedido_x_PlatopModel> pedido = response.body();
                                        PdfComprobante pdfComprobante = new PdfComprobante("Comprobante"+id_p+"_"+fecha_pedido_p+".pdf",pedido,documento);
                                        pdfComprobante.crearPDF();
                                        ((NavigationHost) getActivity()).toastCorrecto("Exito, documento descargado");


                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Pedido_x_PlatopModel>> call, Throwable t) {
                                    Log.e("CallService.onFailure", t.getLocalizedMessage());
                                }
                            });



                        }
                    }

                    @Override
                    public void onFailure(Call<DocumentoModel> call, Throwable t) {
                        Log.e("CallService.onFailure", t.getLocalizedMessage());
                    }
                });





            }
        });

        detalle_pedido_informacion_envio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (estado_p.equals("Entregado") || estado_p.equals("Preparado")) {



                    MainActivity myActivity = (MainActivity) getContext();
                    Bundle datosAEnviar = new Bundle();
                    datosAEnviar.putInt("id_pedido_p", id_p);
                    datosAEnviar.putString("restaurante_p",detalle_edit_text_restaurante.getText().toString());
                    datosAEnviar.putString("direccion_p",direccion_nombre);

                    Fragment fragmento = new Envio_Cliente_Detalle_Fragment();

                    fragmento.setArguments(datosAEnviar);

                    FragmentManager fragmentManager = myActivity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_home, fragmento);
                    fragmentTransaction.addToBackStack(null);


                    fragmentTransaction.commit();
                }

                else{
                    MainActivity myActivity = (MainActivity) getContext();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(myActivity);
                    alertDialogBuilder.setTitle("Informacion de Envio");
                    alertDialogBuilder
                            .setMessage("Su envio aun no ha sido confirmado")
                            .setCancelable(false)
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();

                                }
                            })
                            .create().show();
                }

            }
        });

        detalle_pedido_boton_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity myActivity = (MainActivity)getContext();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(myActivity);
                alertDialogBuilder.setTitle("Cancelar Pedido");
                alertDialogBuilder
                        .setMessage("¿Deseas cancelar el pedido?")
                        .setCancelable(false)
                        .setPositiveButton("Si",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog,int id) {

                                Bundle datosAEnviar = new Bundle();
                                datosAEnviar.putInt("id_pedido_p", id_p);


                                Fragment fragmento = new Cancelacion_Fragment();

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
        });
        detalle_pedido_boton_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (detalle_pedido_fecha.getText().toString().equals("") || detalle_pedido_hora.getText().toString().equals("")) {
                    ((NavigationHost) getActivity()).toastIncorrecto("Error, rellene los datos");
                } else {
                    boolean validar = validar_hora_fecha_actual(fecha_p, hora_final_p);

                    if(validar == true){


                        MainActivity myActivity = (MainActivity)getContext();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(myActivity);
                        alertDialogBuilder.setTitle("Actualizar Pedido");
                        alertDialogBuilder
                                .setMessage("¿Deseas actualizar el pedido?")
                                .setCancelable(false)
                                .setPositiveButton("Si",new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog,int id) {


                                        actualizar_pedido(id_p,hora_inicio_p,hora_final_p,fecha_p,costo_p,estado_p,envio_costo_p,id_tipo_pedido,Integer.parseInt(detalle_pedido_id_restaurante.getText().toString()),Integer.parseInt(detalle_pedido_id_direccion.getText().toString()));
                                        ((NavigationHost) getActivity()).navigateTo(new Fragment_Historial_Lista(),true);
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
        detalle_pedido_boton_feha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity myActivity = (MainActivity)getContext();
                Calendar cal = Calendar.getInstance();
                int anios = cal.get(Calendar.YEAR);
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

                        String fecha_editada = obtener_fecha_nombres(days,mess,anios);

                        detalle_pedido_fecha.setText(fecha_editada);

                        String fecha= year + "-" + fmtmes + "-" + fmtdays;
                        //Asignar Fecha
                        fecha_p= fecha;


                    }
                },anios,mes,dia);
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
        detalle_pedido_boton_hora.setOnClickListener(new View.OnClickListener() {
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
                            String hora_plazo = aumentar_minutos(i,i1,plazo_pedido_p);
                            hora_inicio_p = hora_seleccionada;
                            hora_final_p = hora_plazo;




                            detalle_pedido_hora.setText("De " + hora_seleccionada +" a " + hora_plazo );

                        } else {
                            ((NavigationHost) getActivity()).toastIncorrecto("Error, hora no permitida");
                        }





                    }
                },hora,minutos,true);
                time.show();


            }
        });
        detalle_pedido_boton_agregar_direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
        detalle_pedido_ver_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        if(id_tipo_pedido == 1){
            detalle_spinner_direccion.setVisibility(View.INVISIBLE);
            detalle_pedido_boton_agregar_direccion.setVisibility(View.GONE);
            detalle_pedido_monto_envio.setVisibility(View.GONE);
            detalle_pedido_informacion_envio.setVisibility(View.GONE);
            mensaje_envio.setVisibility(View.GONE);
            mensaje_direccion.setVisibility(View.GONE);


        }


        if(estado_p.equals("Cancelado") || estado_p.equals("Entregado") || estado_p.equals("Preparado")){
            detalle_pedido_boton_actualizar.setVisibility(View.GONE);
            detalle_pedido_boton_eliminar.setVisibility(View.GONE);
            detalle_spinner_direccion.setEnabled(false);
            detalle_pedido_boton_feha.setEnabled(false);
            detalle_pedido_boton_hora.setEnabled(false);
            detalle_pedido_boton_agregar_direccion.setEnabled(false);

        }
        if(estado_p.equals("Cancelado")){
            detalle_pedido_informacion_envio.setVisibility(View.GONE);

        }


        return view;



    }


    public void asignar_hora_text_view(){
        String hora_inicial = hora_inicio_p.substring(0,5);
        String hora_final = hora_final_p.substring(0,5);
        detalle_pedido_hora.setText("De " + hora_inicial +" a " + hora_final );
    }

    public  void asignar_fecha_text_view(){

        Integer anio = Integer.parseInt(fecha_p.substring(0,4));
        Integer mes = Integer.parseInt(fecha_p.substring(5,7));
        Integer dia= Integer.parseInt(fecha_p.substring(8,10));
        String fecha_nombre = obtener_fecha_nombres(dia,mes,anio);
        detalle_pedido_fecha.setText(fecha_nombre);

    }


    public String obtener_fecha_nombres(int dia,int mes,int anio){
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
                    detalle_pedido_id_restaurante.setText(restaurante.getIdrestaurante().toString());
                    detalle_edit_text_restaurante.setText(restaurante.getDistrito() +" "+  restaurante.getLocalidad());

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

        Integer index_lista = 0;
        String nombre = "";

        for (Usuari_Direccion_Model usuari_direccion_model:direccion) {

            if (id_direccion_p == usuari_direccion_model.getIddireccion()){

                index_lista = direccion.indexOf(usuari_direccion_model);
                nombre = usuari_direccion_model.getAlias() + " "  + usuari_direccion_model.getDistrito() + " " + usuari_direccion_model.getLocalidad();


            }

        }


        direccion_nombre =nombre;
        detalle_pedido_id_direccion.setText(id_direccion_p.toString());


        ArrayAdapter<Usuari_Direccion_Model> direccion_adapter= new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line,direccion);
        detalle_spinner_direccion.setAdapter(direccion_adapter);
        detalle_spinner_direccion.setSelection(index_lista);
        detalle_spinner_direccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Integer id = direccion.get(i).getIddireccion();

                detalle_pedido_id_direccion.setText(id.toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Log.e("====",detalle_pedido_id_direccion.getText().toString());


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

    public void listar_platos(View view,Integer pedido){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(PlatoApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        PedidoApi svc = retrofit.create(PedidoApi.class);

        //CALL
        Call<List<Pedido_x_PlatopModel>> lista_valores = svc.listar_pedido_x_plato(pedido);
        lista_valores.enqueue(new Callback<List<Pedido_x_PlatopModel>>() {


            @Override
            public void onResponse(Call<List<Pedido_x_PlatopModel>> call, Response<List<Pedido_x_PlatopModel>> response) {
                if(!response.isSuccessful()){
                    Log.e("CallService.onResponse","Error" + response.code());
                }
                else{

                    List<Pedido_x_PlatopModel> pedido = response.body();



                    RecyclerView recyclerView = view.findViewById(R.id.rv_detalle_pedido_lista_grid);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false));

                    Log.e("CallService.onResponse","Error");

                    Pedido_Adapter adapter = new Pedido_Adapter(pedido);

                    recyclerView.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Call<List<Pedido_x_PlatopModel>> call, Throwable t) {
                Log.e("CallService.onFailure", t.getLocalizedMessage());
            }
        });
    }

    private void actualizar_pedido(Integer id_pedido,String hora_inicio,String hora_final,String fecha,Double total,String estado,Double precio_entrega,Integer id_tipo,Integer id_restaurante, Integer id_direccion){


        Retrofit retrofit = new Retrofit.Builder().baseUrl(PedidoApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        PedidoApi svc = retrofit.create(PedidoApi.class);
        Call<PedidoModel> call = svc.actualizar_pedido(new PedidoModel(id_pedido,hora_inicio,hora_final,fecha,total,estado,precio_entrega,id_tipo,id_restaurante,id_direccion));
        call.enqueue(new Callback<PedidoModel>() {

            @Override
            public void onResponse(Call<PedidoModel> call, Response<PedidoModel> response) {


            }
            @Override
            public void onFailure(Call<PedidoModel> call, Throwable t) {

            }
        });
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