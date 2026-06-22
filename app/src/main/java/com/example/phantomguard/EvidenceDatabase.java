package com.example.phantomguard;


import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class EvidenceDatabase
        extends SQLiteOpenHelper {


    public EvidenceDatabase(Context c){

        super(
                c,
                "phantom.db",
                null,
                1
        );

    }



    @Override
    public void onCreate(SQLiteDatabase db){


        db.execSQL(
                "CREATE TABLE evidence("+
                        "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "photo TEXT,"+
                        "wifi TEXT,"+
                        "time TEXT)"
        );


    }



    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldV,
            int newV
    ){

        db.execSQL(
                "DROP TABLE evidence"
        );

        onCreate(db);

    }




    public void saveEvidence(
            String photo,
            String wifi
    ){


        SQLiteDatabase db =
                getWritableDatabase();



        ContentValues values =
                new ContentValues();



        values.put(
                "photo",
                photo
        );


        values.put(
                "wifi",
                wifi
        );


        values.put(
                "time",
                String.valueOf(
                        System.currentTimeMillis()
                )
        );



        db.insert(
                "evidence",
                null,
                values
        );


    }



    public Cursor getAll(){


        return getReadableDatabase()
                .rawQuery(
                        "SELECT * FROM evidence",
                        null
                );


    }



}