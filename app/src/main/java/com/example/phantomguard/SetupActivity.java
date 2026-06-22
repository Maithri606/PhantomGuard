package com.example.phantomguard;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.*;
import android.Manifest;
import androidx.core.app.ActivityCompat;

import androidx.appcompat.app.AppCompatActivity;


public class SetupActivity extends AppCompatActivity {


    EditText email;

    Button setupBtn;

    TextView deviceText;


    SharedPreferences pref;



    @Override
    protected void onCreate(Bundle b){

        super.onCreate(b);
        ActivityCompat.requestPermissions(
                this,
                new String[]{

                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.VIBRATE

                },
                100
        );

        setContentView(R.layout.activity_setup);



        email=findViewById(R.id.email);

        setupBtn=findViewById(R.id.setupBtn);

        deviceText=findViewById(R.id.device);



        pref=getSharedPreferences(
                "device",
                MODE_PRIVATE
        );



        String id =
                Settings.Secure.getString(
                        getContentResolver(),
                        Settings.Secure.ANDROID_ID
                );


        deviceText.setText(
                "Device ID : "+id
        );



        setupBtn.setOnClickListener(v->{



            String mail =
                    email.getText()
                            .toString()
                            .trim();



            if(mail.isEmpty()){


                email.setError(
                        "Enter Email"
                );

                return;

            }




            pref.edit()

                    .putString(
                            "email",
                            mail
                    )

                    .putString(
                            "device",
                            id
                    )

                    .putBoolean(
                            "setup",
                            true
                    )

                    .apply();



            startService(
                    new Intent(
                            this,
                            PhantomGuardService.class
                    )
            );



            startActivity(
                    new Intent(
                            this,
                            MainActivity.class
                    )
            );


            finish();



        });


    }


}