package com.example.phantomguard;


import android.app.*;
import android.content.*;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.*;
import android.widget.Toast;



public class PhantomGuardService extends Service {


    CameraHelper cameraHelper;
    WifiScanner wifiScanner;



    @Override
    public void onCreate(){

        super.onCreate();


        cameraHelper =
                new CameraHelper(this);


        wifiScanner =
                new WifiScanner(this);



        createNotification();


    }





    @Override
    public int onStartCommand(
            Intent intent,
            int flags,
            int startId
    ){



        if(intent!=null){


            String action =
                    intent.getAction();



            if("ACTIVATE".equals(action)){

                activate();

            }



            if("CATCH".equals(action)){

                alert();

            }


        }



        return START_STICKY;

    }







    private void activate(){


        Toast.makeText(
                this,
                "Collecting Evidence...",
                Toast.LENGTH_LONG
        ).show();



        wifiScanner.scan();



        if(MainActivity.instance != null){


            cameraHelper.capturePhoto(
                    MainActivity.instance,
                    path -> {


                        EvidenceDatabase db =
                                new EvidenceDatabase(this);



                        db.saveEvidence(
                                path,
                                wifiScanner.getData()
                        );



                        EmailSender sender =
                                new EmailSender(this);



                        sender.send(path);



                        Toast.makeText(
                                this,
                                "Photo + Email Sent",
                                Toast.LENGTH_LONG
                        ).show();



                    }
            );


        }
        else{


            Toast.makeText(
                    this,
                    "Camera unavailable",
                    Toast.LENGTH_LONG
            ).show();


        }



    }




    private void alert(){


        Toast.makeText(
                this,
                "⚠ INTRUDER DETECTED ⚠",
                Toast.LENGTH_LONG
        ).show();





        Vibrator vibrator =
                (Vibrator)
                        getSystemService(
                                VIBRATOR_SERVICE);



        if(Build.VERSION.SDK_INT>=26){


            vibrator.vibrate(
                    VibrationEffect.createOneShot(
                            5000,
                            VibrationEffect.DEFAULT_AMPLITUDE
                    )
            );


        }
        else{


            vibrator.vibrate(5000);


        }




        try{


            CameraManager cm =
                    (CameraManager)
                            getSystemService(
                                    CAMERA_SERVICE);



            String id =
                    cm.getCameraIdList()[0];



            cm.setTorchMode(
                    id,
                    true
            );


        }
        catch(Exception e){


            e.printStackTrace();

        }




    }







    private void createNotification(){



        String channelID =
                "phantom_channel";



        if(Build.VERSION.SDK_INT>=26){



            NotificationChannel channel =
                    new NotificationChannel(
                            channelID,
                            "Phantom Guard",
                            NotificationManager.IMPORTANCE_LOW
                    );



            getSystemService(
                    NotificationManager.class
            )
                    .createNotificationChannel(channel);



        }





        Notification notification =
                new Notification.Builder(
                        this,
                        channelID
                )

                        .setContentTitle(
                                "Phantom Guard Active"
                        )

                        .setContentText(
                                "Device Protection Running"
                        )

                        .setSmallIcon(
                                android.R.drawable.ic_lock_lock
                        )

                        .build();




        startForeground(
                10,
                notification
        );


    }





    @Override
    public android.os.IBinder onBind(Intent i){

        return null;

    }


}