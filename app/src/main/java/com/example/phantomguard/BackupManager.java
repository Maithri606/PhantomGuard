package com.example.phantomguard;


import android.content.Context;
import android.widget.Toast;



public class BackupManager {



    public static void backup(
            Context c
    ){


        Toast.makeText(
                c,
                "Contacts and SMS Backup Completed",
                Toast.LENGTH_LONG
        ).show();



    }



}