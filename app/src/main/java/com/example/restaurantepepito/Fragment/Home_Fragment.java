package com.example.restaurantepepito.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantepepito.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Home_Fragment extends Fragment {

    //FirebaseAuth mauth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*mauth = FirebaseAuth.getInstance();

        mauth.getInstance().signOut();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Log.e("Usuario "," ========== Usuario Logueado");
        }
        else{
            Log.e("Usuario "," ================= Usuario No Logueado");
        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_home_, container, false);
        return view;




    }
}