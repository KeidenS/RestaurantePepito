package com.example.restaurantepepito;

import android.os.Bundle;

import com.example.restaurantepepito.Fragment.Login_Fragment;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class LoginActivity extends AppCompatActivity implements  NavigationHost{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_login, new Login_Fragment())
                    .commit();
        }

    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack){
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_login, fragment);

        if(addToBackstack){
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }


}