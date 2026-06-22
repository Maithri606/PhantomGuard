package com.example.phantomguard;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;



public class CameraHelper {


    Context context;

    String imagePath;



    public CameraHelper(Context c){

        context = c;

    }



    public String capturePhoto(){


        try{


            // Demo image creation for testing
            // Later replace with CameraX capture


            Bitmap bitmap =
                    BitmapFactory.decodeResource(
                            context.getResources(),
                            android.R.drawable.ic_menu_camera
                    );



            File file =
                    new File(
                            context.getExternalFilesDir(
                                    Environment.DIRECTORY_PICTURES),
                            "intruder_photo.jpg"
                    );



            FileOutputStream fos =
                    new FileOutputStream(file);



            bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    100,
                    fos
            );


            fos.flush();
            fos.close();



            imagePath =
                    file.getAbsolutePath();



            Toast.makeText(
                    context,
                    "Photo Captured",
                    Toast.LENGTH_SHORT
            ).show();



        }
        catch(Exception e){

            e.printStackTrace();

        }


        return imagePath;


    }


}