package com.example.phantomguard;


import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class EvidenceActivity extends AppCompatActivity {


    TextView evidenceText;


    @Override
    protected void onCreate(Bundle b){

        super.onCreate(b);

        setContentView(R.layout.activity_evidence);



        evidenceText =
                findViewById(R.id.evidenceText);



        loadEvidence();


    }



    private void loadEvidence(){


        EvidenceDatabase db =
                new EvidenceDatabase(this);



        Cursor cursor =
                db.getAll();



        StringBuilder result =
                new StringBuilder();



        if(cursor.getCount()==0){


            result.append(
                    "No Evidence Captured Yet"
            );


        }
        else{


            while(cursor.moveToNext()){


                result.append("\n\n")
                        .append("📷 Photo: ")
                        .append(
                                cursor.getString(1)
                        )

                        .append("\n")

                        .append("📶 WiFi:\n")
                        .append(
                                cursor.getString(2)
                        )

                        .append("\n")

                        .append("⏰ Time: ")
                        .append(
                                cursor.getString(3)
                        );



            }


        }


        evidenceText.setText(result.toString());


    }


}