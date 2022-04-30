package com.example.restaurantepepito.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.restaurantepepito.Model.Pedido_x_PlatopModel;

import java.util.ArrayList;

public class DbPedido_x_Plato extends DbHelper{

    Context context;

    public DbPedido_x_Plato(@Nullable Context context) {
        super(context);
        this.context = context;
    }



    public long insertarPedido(String nombre, Double precio, Integer cantidad, Double subtotal, Integer id_plato){

        long id = 0;

        try{

            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre",nombre);
            values.put("precio",precio);
            values.put("cantidad",cantidad);
            values.put("subtotal",subtotal);
            values.put("id_plato",id_plato);

            id = db.insert(TABLE_PEDIDO_X_PLATO,null,values);

        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public ArrayList<Pedido_x_PlatopModel> mostrarPedidos(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Pedido_x_PlatopModel> lista_pedidos = new ArrayList<>();
        Pedido_x_PlatopModel pedidoModel = null;
        Cursor cursorPedidos = null;

        cursorPedidos = db.rawQuery("SELECT * FROM " + TABLE_PEDIDO_X_PLATO, null);

        if(cursorPedidos.moveToFirst()){
            do {
                pedidoModel = new Pedido_x_PlatopModel();
                pedidoModel.setId_pedido(cursorPedidos.getInt(0));
                pedidoModel.setNombre(cursorPedidos.getString(1));
                pedidoModel.setPrecio(cursorPedidos.getDouble(2));
                pedidoModel.setCantidad(cursorPedidos.getInt(3));
                pedidoModel.setSubtotal(cursorPedidos.getDouble(4));
                pedidoModel.setId_plato(cursorPedidos.getInt(5));
                lista_pedidos.add(pedidoModel);
            }while(cursorPedidos.moveToNext());
        }

        cursorPedidos.close();
        return lista_pedidos;

    }

    public boolean eliminarPedido(int id_pedido){
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        try{
            db.execSQL("DELETE FROM " + TABLE_PEDIDO_X_PLATO + " WHERE id_pedido = '" + id_pedido + "'");
            correcto = true;
        }catch (Exception ex){
            ex.toString();
            correcto = false;
        }finally {
            db.close();;
        }
        return correcto;

    }

}
