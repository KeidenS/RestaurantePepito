package com.example.restaurantepepito;

import androidx.fragment.app.Fragment;

public interface NavigationHost {

        void navigateTo(Fragment fragment, boolean addToBackstack);

        void toastIncorrecto(String msg);

        void toastCorrecto(String msg);
}
