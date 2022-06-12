package com.example.restaurantepepito.Adapter;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantepepito.Holder.HistorialHolder;
import com.example.restaurantepepito.Holder.Plato_Holder;
import com.example.restaurantepepito.Model.PedidoModel;
import com.example.restaurantepepito.Model.PlatoModel;
import com.example.restaurantepepito.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialHolder>{

    List<PedidoModel> pedido_lista;

    public HistorialAdapter(List<PedidoModel> pedido_lista) {
        this.pedido_lista = pedido_lista;
    }

    public HistorialAdapter() {

    }

    @NonNull
    @Override
    public HistorialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_historial_card, parent, false);
        return new HistorialHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialHolder holder, int position) {
        if (pedido_lista != null && position < pedido_lista.size()) {

            PedidoModel pedidoModel = pedido_lista.get(position);


            String nombre = pedidoModel.getNombre();
            String fecha= pedidoModel.getFecha_pedido();
            Double total= Double.parseDouble(pedidoModel.getTotal().toString());
            String estado= pedidoModel.getEstado();

            Integer id_pedido_p= pedidoModel.getIdpedido();
            Integer id_restaurante_p= pedidoModel.getIdrestaurante();
            Integer id_direccion_p= pedidoModel.getIddireccion();
            Integer id_tipo_pedido_p= pedidoModel.getIdtipo_pedido();
            String plazo_pedido_p= pedidoModel.getPlazo();
            String fecha_p= pedidoModel.getFecha();
            String hora_inicial_p= pedidoModel.getHora_inicio();
            String hora_final_p= pedidoModel.getHora_final();
            Double envio_costo_p = pedidoModel.getPrecio_entrega();
            String estado_p = pedidoModel.getEstado();
            String fecha_pedido_p=pedidoModel.getFecha_pedido();

            Integer anio = Integer.parseInt(fecha.substring(0,4));
            Integer mes = Integer.parseInt(fecha.substring(5,7));
            Integer dia= Integer.parseInt(fecha.substring(8,10));
            String subtotal = "S/"+String.format("%.2f",pedidoModel.getTotal());
            holder.estado_p = estado_p;
            holder.fecha_pedido_p = fecha_pedido_p;
            holder.envio_costo = envio_costo_p;
            holder.id_restaurante = id_restaurante_p;
            holder.id_direccion = id_direccion_p;
            holder.plazo_p = plazo_pedido_p;
            holder.id_p = id_pedido_p;
            holder.id_tipo_pedido_p = id_tipo_pedido_p;
            holder.fecha_p = fecha_p;
            holder.hora_final_p = hora_final_p;
            holder.hora_inicio_p = hora_inicial_p;
            holder.nombre.setText(nombre);
            holder.fecha.setText(asignar_fecha_text_view(dia,mes,anio));
            holder.total.setText(subtotal);
            holder.estado.setText(estado);



            if(id_tipo_pedido_p == 1){
                holder.imagen.setImageResource(R.drawable.recogo);
            }
            else{
                holder.imagen.setImageResource(R.drawable.delivery);
            }

            if(estado_p.equals("Confirmado")){
                holder.imagen.setBackgroundResource(R.color.amarillo_h);
            }else if (estado_p.equals("Cancelado")){
                holder.imagen.setBackgroundResource(R.color.rojo_h);
            }else if (estado_p.equals("Preparado")){
                holder.imagen.setBackgroundResource(R.color.verde_h);
            }else{
                holder.imagen.setBackgroundResource(R.color.verde_h);
            }

            holder.setOnclickListeners();
        }
    }


    @Override
    public int getItemCount() {
        return pedido_lista.size();
    }

    public void filterList(List<PedidoModel> lista_filtrada){
        pedido_lista = lista_filtrada;
        notifyDataSetChanged();
    }

    public String asignar_fecha_text_view(int dia, int mes, int anio){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha = formato.parse(dia+"/"+mes+"/"+anio);
            Locale idioma = Locale.getDefault();
            String diaYMes = (new SimpleDateFormat("EEEE dd 'de' MMMM 'del' YYYY", idioma)).format(fecha);
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