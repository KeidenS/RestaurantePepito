package com.example.restaurantepepito;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.restaurantepepito.Fragment.Login_Fragment;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


public class LoginActivity extends AppCompatActivity implements  NavigationHost{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {
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