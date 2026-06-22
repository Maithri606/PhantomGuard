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

    MediaPlayer player;




    @Override
    public void onCreate() {

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
    ) {



        if(intent != null){


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
                "🚨 INTRUDER DETECTED 🚨",
                Toast.LENGTH_LONG
        ).show();







        // VIBRATION

        Vibrator vibrator =
                (Vibrator)
                        getSystemService(
                                VIBRATOR_SERVICE
                        );




        if(vibrator != null){



            if(Build.VERSION.SDK_INT >= 26){


                vibrator.vibrate(

                        VibrationEffect.createWaveform(

                                new long[]{
                                        0,
                                        500,
                                        300,
                                        500,
                                        300,
                                        1000
                                },

                                -1

                        )

                );


            }
            else{


                vibrator.vibrate(5000);

            }

        }









        // FLASHLIGHT


        try{


            CameraManager cameraManager =
                    (CameraManager)
                            getSystemService(
                                    CAMERA_SERVICE
                            );



            String cameraId =
                    cameraManager
                            .getCameraIdList()[0];




            new Thread(() -> {


                try{


                    for(int i=0;i<10;i++){



                        cameraManager.setTorchMode(
                                cameraId,
                                true
                        );


                        Thread.sleep(300);



                        cameraManager.setTorchMode(
                                cameraId,
                                false
                        );


                        Thread.sleep(300);



                    }


                }
                catch(Exception e){

                    e.printStackTrace();

                }



            }).start();



        }
        catch(Exception e){

            e.printStackTrace();

        }








        // ALARM SOUND


        player =
                MediaPlayer.create(
                        this,
                        R.raw.alarm
                );


        if (player != null) {

            player.setLooping(true);
            player.start();

            // STOP AFTER 5 SECONDS
            new android.os.Handler(
                    android.os.Looper.getMainLooper()
            ).postDelayed(() -> {

                if (player != null && player.isPlaying()) {
                    player.stop();
                    player.release();
                    player = null;
                }

            }, 5000);
        }








        // HIGH ALERT NOTIFICATION


        NotificationManager nm =
                (NotificationManager)
                        getSystemService(
                                NOTIFICATION_SERVICE
                        );




        Notification notification =
                new Notification.Builder(
                        this,
                        "phantom_channel"
                )


                        .setContentTitle(
                                "🚨 PHANTOM GUARD ALERT"
                        )


                        .setContentText(
                                "Intruder detected!"
                        )


                        .setSmallIcon(
                                android.R.drawable.ic_dialog_alert
                        )


                        .setPriority(
                                Notification.PRIORITY_MAX
                        )


                        .setAutoCancel(false)


                        .build();




        nm.notify(
                99,
                notification
        );



    }









    private void createNotification(){



        String channelID =
                "phantom_channel";





        if(Build.VERSION.SDK_INT >= 26){



            NotificationChannel channel =

                    new NotificationChannel(

                            channelID,

                            "Phantom Guard Alert",

                            NotificationManager
                                    .IMPORTANCE_HIGH

                    );



            channel.setDescription(
                    "Security alert service"
            );



            channel.enableVibration(true);



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
    public void onDestroy(){


        if(player != null){


            player.stop();

            player.release();

            player=null;

        }



        super.onDestroy();


    }










    @Override
    public android.os.IBinder onBind(Intent intent){

        return null;

    }


}