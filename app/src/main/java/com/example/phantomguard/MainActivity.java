package com.example.phantomguard;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    public static MainActivity instance;

    TextView status, device;

    Button activate, catchBtn, history;



    @Override
    protected void onCreate(Bundle b){

        super.onCreate(b);

        setContentView(R.layout.activity_main);


        // IMPORTANT
        instance = this;



        status = findViewById(R.id.status);

        device = findViewById(R.id.device);

        activate = findViewById(R.id.activate);

        catchBtn = findViewById(R.id.catchBtn);

        history = findViewById(R.id.history);




        device.setText(

                "Device Protected\n\nID : " +

                        getSharedPreferences(
                                "device",
                                MODE_PRIVATE
                        )
                                .getString(
                                        "device",
                                        "unknown"
                                )

        );




        activate.setOnClickListener(v->{


            Intent i =
                    new Intent(
                            this,
                            PhantomGuardService.class
                    );


            i.setAction("ACTIVATE");


            startService(i);



            status.setText(
                    "Evidence Collection Running"
            );


        });






        catchBtn.setOnClickListener(v->{


            Intent i =
                    new Intent(
                            this,
                            PhantomGuardService.class
                    );


            i.setAction("CATCH");


            startService(i);



            status.setText(
                    "⚠ INTRUDER ALERT"
            );


        });






        history.setOnClickListener(v->{


            startActivity(
                    new Intent(
                            this,
                            EvidenceActivity.class
                    )
            );


        });




        // Camera permission

        if(checkSelfPermission(
                android.Manifest.permission.CAMERA)

                != PackageManager.PERMISSION_GRANTED){


            requestPermissions(
                    new String[]{
                            android.Manifest.permission.CAMERA
                    },
                    100
            );

        }


    }


}