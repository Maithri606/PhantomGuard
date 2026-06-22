package com.example.phantomguard;


import android.content.Context;
import android.widget.Toast;



public class AppLockManager {



    public static void lockApps(
            Context c
    ){


        Toast.makeText(
                c,
                "UPI, Banking, Gallery Locked",
                Toast.LENGTH_LONG
        ).show();



    }



}