package com.example.restaurantepepito.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "carrito";
    public static final String TABLE_PEDIDO_X_PLATO = "pedido_x_plato";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PEDIDO_X_PLATO + "(" +
                "id_pedido INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nombre TEXT NOT NULL, "+
                "precio DOUBLE NOT NULL, "+
                "cantidad INTEGER NOT NULL, "+
                "subtotal DOUBLE NOT NULL, "+
                "id_plato INTEGER NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_PEDIDO_X_PLATO);
        onCreate(sqLiteDatabase);
    }
}
