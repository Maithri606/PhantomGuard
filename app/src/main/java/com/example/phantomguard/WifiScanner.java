package com.example.phantomguard;


import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;



public class WifiScanner {



    Context context;

    String data="";



    public WifiScanner(Context c){

        context=c;

    }



    public void scan(){


        WifiManager manager =

                (WifiManager)

                        context.getApplicationContext()
                                .getSystemService(
                                        Context.WIFI_SERVICE
                                );



        List<ScanResult> list =

                manager.getScanResults();



        StringBuilder sb =
                new StringBuilder();



        for(ScanResult r:list){


            sb.append(
                            "SSID: "
                    )
                    .append(r.SSID)

                    .append("\nSignal: ")

                    .append(r.level)

                    .append(" dBm\n\n");


        }



        data=sb.toString();


    }




    public String getData(){

        return data;

    }



}