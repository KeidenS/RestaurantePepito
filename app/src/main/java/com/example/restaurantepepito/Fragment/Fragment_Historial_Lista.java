package com.example.restaurantepepito.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.restaurantepepito.Adapter.HistorialAdapter;
import com.example.restaurantepepito.Adapter.Plato_Adapter;
import com.example.restaurantepepito.Api.PedidoApi;
import com.example.restaurantepepito.Api.PlatoApi;
import com.example.restaurantepepito.MainActivity;
import com.example.restaurantepepito.Model.PedidoModel;
import com.example.restaurantepepito.Model.PlatoModel;
import com.example.restaurantepepito.R;
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


public class Fragment_Historial_Lista extends Fragment {


    FirebaseAuth mAuth;
    private EditText filtrar;
    public List<PedidoModel> pedido_lista_original;
    RecyclerView recyclerView;
    HistorialAdapter adapter = new HistorialAdapter();
    Button historial_fecha;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historial_lista, container, false);
        filtrar = view.findViewById(R.id.historial_buscar);
        recyclerView = view.findViewById(R.id.rv_historial_lista_grid);
        historial_fecha = view.findViewById(R.id.historial_buscar_fecha);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false));

        filtrar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString(),view);
            }
        });

        historial_fecha.setOnClickListener(new View.OnClickListener() {
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


                        String fecha= year + "-" + fmtmes + "-" + fmtdays;
                        filtrar.setText(fecha);

                    }
                },anios,mes,dia);
                Date date =  new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_WEEK, 5);
                Date dateMod = calendar.getTime();

                Log.e("====",new Date().getTime() +"");
                Log.e("====",dateMod.getTime() +"");

                dpd.getDatePicker().setMaxDate(new Date().getTime());
                dpd.show();
            }
        });

        listar_pedidos(view);
        return view;
    }

    private void filter(String text,View view) {
        List<PedidoModel> lista_filtrada = new ArrayList<>();

        for (PedidoModel item: pedido_lista_original) {
            if(item.getFecha_pedido().toLowerCase().contains(text.toLowerCase())){
                lista_filtrada.add(item);
            }
        }



        adapter.filterList(lista_filtrada);

    }

    public void listar_pedidos(View view){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(PedidoApi.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        PedidoApi svc = retrofit.create(PedidoApi.class);
        String id = mAuth.getCurrentUser().getUid();
        //CALL
        Call<List<PedidoModel>> lista_valores = svc.listar_pedido(id);
        lista_valores.enqueue(new Callback<List<PedidoModel>>() {


            @Override
            public void onResponse(Call<List<PedidoModel>> call, Response<List<PedidoModel>> response) {
                if(!response.isSuccessful()){
                    Log.e("CallService.onResponse","Error" + response.code());
                }
                else{

                    pedido_lista_original = response.body();








                    adapter = new HistorialAdapter(pedido_lista_original);

                    recyclerView.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Call<List<PedidoModel>> call, Throwable t) {
                Log.e("CallService.onFailure", t.getLocalizedMessage());
            }
        });
    }




}