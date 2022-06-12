package com.example.restaurantepepito;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantepepito.Fragment.Carrito_Gestionar_Fragment;
import com.example.restaurantepepito.Fragment.Fragment_Historial_Lista;
import com.example.restaurantepepito.Fragment.Home_Fragment;
import com.example.restaurantepepito.Fragment.Plato_Lista_Fragment;
import com.example.restaurantepepito.Fragment.Usuario_Actualizar_Fragment;
import com.example.restaurantepepito.SQLite.DbHelper;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements  NavigationHost {


    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_home, new Fragment_Historial_Lista())
                    .commit();
        }

        //Navegacion Barra

        DbHelper dbHelper = new DbHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) actionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close){
            @Override
            public void onDrawerClosed(View drawerView){
                MenuItem item= navigationView.getCheckedItem();
                if (item!=null) navegar(item.getItemId());
            }
        };

        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            navigationView.setCheckedItem(item);
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        });






    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.openDrawer(GravityCompat.END);
        else super.onBackPressed();
    }


    private void navegar(int id_menu){
        if(id_menu == R.id.menu_plato) navigateTo(new Plato_Lista_Fragment(),true);
        else if (id_menu == R.id.menu_home) navigateTo(new Home_Fragment(),true);
        else if (id_menu == R.id.menu_mi_cuenta) navigateTo(new Usuario_Actualizar_Fragment(),true);
        else if (id_menu == R.id.menu_carrito) navigateTo(new Carrito_Gestionar_Fragment(),true);
        else if (id_menu == R.id.menu_historial) navigateTo(new Fragment_Historial_Lista(),true);

    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack){
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_home, fragment);

        if(addToBackstack){
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void toastIncorrecto(String msg) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.custom_toast_error, (ViewGroup) findViewById(R.id.ll_custom_toast_error));
            TextView txtmensaje = view.findViewById(R.id.custom_toast_error_text_view);
            txtmensaje.setText(msg);

            android.widget.Toast toast = new android.widget.Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0,200);
            toast.setDuration(android.widget.Toast.LENGTH_LONG);
            toast.setView(view);
            toast.show();


    }

    @Override
    public void toastCorrecto(String msg) {

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_ok, (ViewGroup) findViewById(R.id.ll_custom_toast_ok));
        TextView txtmensaje = view.findViewById(R.id.custom_toast_ok_text_view);
        txtmensaje.setText(msg);

        android.widget.Toast toast = new android.widget.Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0,200);
        toast.setDuration(android.widget.Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();

    }


}