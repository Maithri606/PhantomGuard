package com.example.phantomguard;


import android.content.Context;
import android.os.Environment;
import android.widget.Toast;


import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageCapture.OutputFileOptions;
import androidx.camera.core.ImageCapture.OutputFileResults;

import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;


import java.io.File;



public class CameraHelper {


    private Context context;



    public CameraHelper(Context c){

        context = c;

    }




    public void capturePhoto(
            LifecycleOwner owner,
            CaptureCallback callback
    ){



        ProcessCameraProvider
                .getInstance(context)
                .addListener(() -> {



                            try {



                                ProcessCameraProvider provider =
                                        ProcessCameraProvider
                                                .getInstance(context)
                                                .get();




                                ImageCapture imageCapture =
                                        new ImageCapture.Builder()

                                                .setCaptureMode(
                                                        ImageCapture
                                                                .CAPTURE_MODE_MINIMIZE_LATENCY
                                                )

                                                .build();





                                CameraSelector cameraSelector =
                                        new CameraSelector.Builder()

                                                .requireLensFacing(
                                                        CameraSelector
                                                                .LENS_FACING_FRONT
                                                )

                                                .build();





                                provider.unbindAll();





                                provider.bindToLifecycle(
                                        owner,
                                        cameraSelector,
                                        imageCapture
                                );







                                File photoFile =
                                        new File(

                                                context.getExternalFilesDir(
                                                        Environment
                                                                .DIRECTORY_PICTURES
                                                ),

                                                "intruder_photo.jpg"
                                        );







                                OutputFileOptions outputOptions =

                                        new OutputFileOptions.Builder(
                                                photoFile
                                        )
                                                .build();








                                imageCapture.takePicture(

                                        outputOptions,


                                        ContextCompat.getMainExecutor(context),


                                        new ImageCapture.OnImageSavedCallback(){





                                            @Override
                                            public void onImageSaved(
                                                    OutputFileResults output
                                            ){



                                                String path =
                                                        photoFile
                                                                .getAbsolutePath();





                                                Toast.makeText(

                                                        context,

                                                        "Photo Saved",

                                                        Toast.LENGTH_SHORT

                                                ).show();





                                                callback.done(path);

                                            }






                                            @Override
                                            public void onError(
                                                    ImageCaptureException error
                                            ){



                                                Toast.makeText(

                                                        context,

                                                        "Camera Error",

                                                        Toast.LENGTH_LONG

                                                ).show();




                                                error.printStackTrace();

                                            }




                                        }

                                );






                            }
                            catch(Exception e){


                                e.printStackTrace();


                                Toast.makeText(

                                        context,

                                        "Camera Failed",

                                        Toast.LENGTH_LONG

                                ).show();


                            }





                        },

                        ContextCompat.getMainExecutor(context)

                );





    }








    public interface CaptureCallback{


        void done(String imagePath);


    }




}