package com.example.restaurantepepito.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurantepepito.Api.PagoApi;
import com.example.restaurantepepito.Api.PedidoApi;
import com.example.restaurantepepito.Api.Usuario_DireccionAPI;
import com.example.restaurantepepito.MainActivity;
import com.example.restaurantepepito.Model.PagoModel;
import com.example.restaurantepepito.Model.PedidoModel;
import com.example.restaurantepepito.Model.Pedido_x_PlatopModel;
import com.example.restaurantepepito.Model.Resultado_Pago;
import com.example.restaurantepepito.Model.Usuari_Direccion_Model;
import com.example.restaurantepepito.NavigationHost;
import com.example.restaurantepepito.R;
import com.example.restaurantepepito.SQLite.DbPedido_x_Plato;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Pago_Fragment extends Fragment {

    TextInputEditText numero_tarjeta;
    TextInputEditText cvv;

    TextInputEditText fecha_anio_vencimiento;
    TextInputEditText fecha_mes_vencimiento;
    Button registrar_pago_pedido;
    TextView monto_total;
    Integer id_pedido;
    private String hora_inicial;
    private String hora_final;
    private String fecha;
    private Double monto;
    private Double monto_envio;
    private Integer id_tipo;
    private Integer id_restaurante;
    private Integer id_direccion;
    FirebaseAuth mAuth;

    TextView fecha_actual_text;
    TextView fecha_pedido_text;
    TextView fecha_hora_text;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        Bundle datosRecuperados = getArguments();
        if (datosRecuperados != null) {
            hora_inicial = datosRecuperados.getString("hora_inicio");
            hora_final = datosRecuperados.getString("hora_final");
            fecha = datosRecuperados.getString("fecha");
            monto = datosRecuperados.getDouble("monto");
            monto_envio = datosRecuperados.getDouble("monto_envio_p");
            id_tipo = datosRecuperados.getInt("id_tipo");
            id_restaurante = datosRecuperados.getInt("id_restaurante");
            id_direccion = datosRecuperados.getInt("id_direccion");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pago, container, false);

        numero_tarjeta = view.findViewById(R.id.pago_numero_tarjeta);
        cvv = view.findViewById(R.id.pago_cvv);

        fecha_anio_vencimiento = view.findViewById(R.id.pago__anio_vencimiento);
        fecha_mes_vencimiento = view.findViewById(R.id.pago_mes_vencimiento);
        registrar_pago_pedido = view.findViewById(R.id.registrar_pago_pedido);
        monto_total = view.findViewById(R.id.pago_monto_total);
        fecha_actual_text = view.findViewById(R.id.fecha_actual_text);
        fecha_pedido_text = view.findViewById(R.id.fecha_epdido_text);
        fecha_hora_text = view.findViewById(R.id.fecha_hora_text);

        fecha_hora_text.setText("De "+ hora_inicial+" a "+hora_final);

        int dia = Integer.parseInt(fecha.substring(8,10));
        int mes = Integer.parseInt(fecha.substring(5,7));
        int anio = Integer.parseInt(fecha.substring(0,4));

        String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());


        int dia2 = Integer.parseInt(timeStamp.substring(8,10));
        int mes2 = Integer.parseInt(timeStamp.substring(5,7));
        int anio2 = Integer.parseInt(timeStamp.substring(0,4));

        if(id_tipo == 1){
            fecha_pedido_text.setText("Recogo para el "+ asignar_fecha_text_view(dia,mes,anio));
        }else{
            fecha_pedido_text.setText("Delivery para el "+ asignar_fecha_text_view(dia,mes,anio));
        }

        monto_total.setText("S/"+String.format("%.2f",monto));
        fecha_actual_text.setText(asignar_fecha_text_view(dia2,mes2,anio2));

        registrar_pago_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cvv.getText().toString().equals("") ||numero_tarjeta.getText().toString().equals("") ||fecha_anio_vencimiento.getText().toString().equals("")||fecha_mes_vencimiento.getText().toString().equals("") ){


                    ((NavigationHost) getActivity()).toastIncorrecto("Error, rellene los campos");

                }
                else{

                    boolean cvv_v;
                    boolean numero_tarjeta_v;
                    boolean mes_v;
                    boolean anio_v;


                    if(cvv.getText().toString().length() == 3 || cvv.getText().length() == 4){
                        cvv_v = true;
                    }
                    else{
                        cvv_v = false;
                    }

                    if(numero_tarjeta.getText().toString().length() == 16 || numero_tarjeta.getText().toString().length() == 14 || numero_tarjeta.getText().toString().length() == 15){
                        numero_tarjeta_v = true;
                    }
                    else{
                        numero_tarjeta_v = false;
                    }
                    if(fecha_mes_vencimiento.getText().toString().length() == 2){
                        mes_v = true;
                    }
                    else{
                        mes_v = false;
                    }
                    if(fecha_anio_vencimiento.getText().toString().length() == 4){
                        anio_v = true;
                    }
                    else{
                        anio_v = false;
                    }

                    if(cvv_v == true && numero_tarjeta_v == true && mes_v == true && anio_v == true){

                        Log.e("======DATOS=====",hora_inicial+"/"+hora_final+"/"+fecha+"/"+monto+"/"+monto_envio+"/"+id_tipo+"/"+id_restaurante+"/"+id_direccion);

                        String monto_total_t = convertir_monto(monto);
                        String numer_tarjeta_t = numero_tarjeta.getText().toString();
                        String cvv_t = cvv.getText().toString();
                        String fecha_anio_t = fecha_anio_vencimiento.getText().toString();
                        String fecha_mes_t = fecha_mes_vencimiento.getText().toString();


                        insertar_pago(numer_tarjeta_t, cvv_t, mAuth.getCurrentUser().getEmail().toString(), fecha_mes_t, fecha_anio_t, "PEN", Integer.parseInt(monto_total_t),hora_inicial, hora_final, fecha, monto, "Confirmado", monto_envio, id_tipo, id_restaurante, id_direccion);




                    }
                    else{
                        ((NavigationHost) getActivity()).toastIncorrecto("Error, datos incorrectos");
                    }


                }


            }
        });

        return view;
    }

    private void insertar_pago(String numero_tarjeta_s, String cvv_s, String email_s, String mes_s, String anio_s, String codigo_moneda_s, Integer monto_s,String hora_inicio, String hora_final, String fecha, Double total, String estado, Double precio_entrega, Integer id_tipo, Integer id_restaurante, Integer id_direccion) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(PagoApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        PagoApi svc = retrofit.create(PagoApi.class);
        Call<Resultado_Pago> call = svc.registrar_pago(new PagoModel(numero_tarjeta_s, cvv_s, "adrianmarcos@gmail.com", mes_s, anio_s, codigo_moneda_s, monto_s));
        call.enqueue(new Callback<Resultado_Pago>() {

            @Override
            public void onResponse(Call<Resultado_Pago> call, Response<Resultado_Pago> response) {
                Resultado_Pago resul = response.body();
                if(resul.getError() == true){


                    MainActivity myActivity = (MainActivity)getContext();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(myActivity);
                    alertDialogBuilder.setTitle("Pago Erroneo");
                    alertDialogBuilder
                            .setMessage(resul.getMensaje())
                            .setCancelable(false)
                            .setPositiveButton("Aceptar",new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog,int id) {
                                    ((NavigationHost) getActivity()).navigateTo(new Pedido_Tipo_Fragment(),false);
                                }}).create().show();

                }


                else{



                    insertar_pedido(hora_inicio, hora_final, fecha, total, estado, precio_entrega, id_tipo, id_restaurante, id_direccion);

                    MainActivity myActivity = (MainActivity)getContext();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(myActivity);
                    alertDialogBuilder.setTitle("Pago Exitoso");
                    alertDialogBuilder
                            .setMessage(resul.getMensaje())
                            .setCancelable(false)
                            .setPositiveButton("Aceptar",new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog,int id) {
                                    ((NavigationHost) getActivity()).navigateTo(new Plato_Lista_Fragment(),false);
                                }}).create().show();

                }
            }

            @Override
            public void onFailure(Call<Resultado_Pago> call, Throwable t) {


            }
        });
    }


    private String convertir_monto(Double monto) {
        Double d = monto;
        DecimalFormat format = new DecimalFormat("0.00");
        String aux = format.format(d);
        String nuevo = aux.replace(".", "");
        return nuevo;

    }

    private void insertar_pedido(String hora_inicio, String hora_final, String fecha, Double total, String estado, Double precio_entrega, Integer id_tipo, Integer id_restaurante, Integer id_direccion) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(PedidoApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        PedidoApi svc = retrofit.create(PedidoApi.class);
        Call<Integer> call = svc.registrar_pedido(new PedidoModel(hora_inicio, hora_final, fecha, total, estado, precio_entrega, id_tipo, id_restaurante, id_direccion));
        call.enqueue(new Callback<Integer>() {

            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                id_pedido = response.body();


                Retrofit retrofit = new Retrofit.Builder().baseUrl(PedidoApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                ArrayList<Pedido_x_PlatopModel> lista = obtener_lista();
                for (Pedido_x_PlatopModel p : lista) {

                    PedidoApi svc = retrofit.create(PedidoApi.class);
                    Pedido_x_PlatopModel pedidoPlato = new Pedido_x_PlatopModel(p.getSubtotal(), p.getCantidad(), id_pedido, p.getId_plato());
                    Call<Pedido_x_PlatopModel> call2 = svc.registrar_pedido_x_plato(pedidoPlato);


                    call2.enqueue(new Callback<Pedido_x_PlatopModel>() {

                        @Override
                        public void onResponse(Call<Pedido_x_PlatopModel> call, Response<Pedido_x_PlatopModel> response) {


                        }

                        @Override
                        public void onFailure(Call<Pedido_x_PlatopModel> call, Throwable t) {

                        }
                    });

                    limpiar_carrito(p);


                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    public ArrayList<Pedido_x_PlatopModel> obtener_lista() {
        DbPedido_x_Plato db_pedido = new DbPedido_x_Plato(getContext());
        return db_pedido.mostrarPedidos();
    }


    public void limpiar_carrito(Pedido_x_PlatopModel objeto) {

        DbPedido_x_Plato db_pedido = new DbPedido_x_Plato(getContext());

        db_pedido.eliminarPedido(objeto.getId_pedido());


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




}