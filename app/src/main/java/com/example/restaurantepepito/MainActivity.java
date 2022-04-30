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
import android.view.MenuItem;
import android.view.View;

import com.example.restaurantepepito.Fragment.Carrito_Fragment;
import com.example.restaurantepepito.Fragment.Home_Fragment;
import com.example.restaurantepepito.Fragment.Login_Fragment;
import com.example.restaurantepepito.Fragment.Plato_Lista_Fragment;
import com.example.restaurantepepito.Fragment.Usuario_Actualizar_Fragment;
import com.example.restaurantepepito.SQLite.DbHelper;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements  NavigationHost{


    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_home, new Home_Fragment())
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
        else if (id_menu == R.id.menu_carrito) navigateTo(new Carrito_Fragment(),true);

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
}