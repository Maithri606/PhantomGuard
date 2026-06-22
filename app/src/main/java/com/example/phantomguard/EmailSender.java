package com.example.phantomguard;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;



public class EmailSender {


    Context context;


    String ownerEmail =
            "srividyaguru1979@gmail.com";


    String appEmail =
            "srividyaguru1979@gmail.com";


    String appPassword =
            "oatbjnxpqllqlwgr";



    public EmailSender(Context c){

        context=c;

    }



    public void send(
            String imagePath
    ){



        new AsyncTask<Void,Void,Void>(){


            protected Void doInBackground(Void... v){


                try{


                    Properties props =
                            new Properties();


                    props.put(
                            "mail.smtp.auth",
                            "true"
                    );


                    props.put(
                            "mail.smtp.starttls.enable",
                            "true"
                    );


                    props.put(
                            "mail.smtp.host",
                            "smtp.gmail.com"
                    );


                    props.put(
                            "mail.smtp.port",
                            "587"
                    );




                    Session session =
                            Session.getInstance(
                                    props,
                                    new javax.mail.Authenticator(){


                                        protected PasswordAuthentication
                                        getPasswordAuthentication(){


                                            return new PasswordAuthentication(
                                                    appEmail,
                                                    appPassword
                                            );

                                        }

                                    });



                    Message message =
                            new MimeMessage(session);



                    message.setFrom(
                            new InternetAddress(
                                    appEmail
                            )
                    );


                    message.setRecipients(
                            Message.RecipientType.TO,
                            InternetAddress.parse(
                                    ownerEmail
                            )
                    );


                    message.setSubject(
                            "⚠ Phantom Guard Alert"
                    );



                    MimeBodyPart text =
                            new MimeBodyPart();


                    text.setText(
                            "Intruder detected.\nEvidence attached."
                    );



                    Multipart multipart =
                            new MimeMultipart();



                    multipart.addBodyPart(text);




                    if(imagePath!=null){


                        MimeBodyPart img =
                                new MimeBodyPart();


                        DataSource source =
                                new FileDataSource(
                                        imagePath
                                );


                        img.setDataHandler(
                                new DataHandler(source)
                        );


                        img.setFileName(
                                "intruder_photo.jpg"
                        );


                        multipart.addBodyPart(img);

                    }




                    message.setContent(
                            multipart
                    );



                    Transport.send(
                            message
                    );



                }
                catch(Exception e){

                    Toast.makeText(
                            context,
                            "Mail Error: " + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();

                    e.printStackTrace();

                }


                return null;

            }



        }.execute();



    }


}