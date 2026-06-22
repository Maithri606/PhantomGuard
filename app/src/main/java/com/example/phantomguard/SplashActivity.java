package com.example.phantomguard;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;



public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle b){

        super.onCreate(b);


        setContentView(R.layout.activity_splash);



        new Handler().postDelayed(()->{


            boolean setup=
                    getSharedPreferences(
                            "device",
                            MODE_PRIVATE
                    )
                            .getBoolean(
                                    "setup",
                                    false
                            );



            if(setup){


                startActivity(
                        new Intent(
                                this,
                                MainActivity.class
                        )
                );


            }
            else{


                startActivity(
                        new Intent(
                                this,
                                SetupActivity.class
                        )
                );


            }


            finish();



        },2000);



    }


}