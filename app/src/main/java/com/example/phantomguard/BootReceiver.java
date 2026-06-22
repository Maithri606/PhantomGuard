package com.example.phantomguard;


import android.content.*;


public class BootReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(
            Context context,
            Intent intent
    ){



        if(
                Intent.ACTION_BOOT_COMPLETED.equals(
                        intent.getAction()
                )
        ){



            Intent service =
                    new Intent(
                            context,
                            PhantomGuardService.class
                    );



            context.startForegroundService(
                    service
            );



        }



    }


}