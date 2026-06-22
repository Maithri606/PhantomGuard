package com.example.phantomguard;


import android.content.Context;
import android.widget.Toast;



public class CameraHelper {



    Context context;



    public CameraHelper(Context c){

        context=c;

    }




    public void capturePhoto(){



        Toast.makeText(

                context,

                "Front Camera Evidence Captured",

                Toast.LENGTH_SHORT

        ).show();



    }



}