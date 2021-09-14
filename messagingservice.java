package com.example.pavilion.remind2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by PAVILION on 17/05/2018.
 */

public class messagingservice extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String messagetitle=remoteMessage.getNotification().getTitle();
        String messagebody=remoteMessage.getNotification().getBody();
        String click=remoteMessage.getNotification().getClickAction();
        String datamessage=remoteMessage.getData().get("message");
        NotificationCompat.Builder mbuilder=
                new NotificationCompat.Builder(this,getString(R.string.default_notification_channel_id))
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(messagetitle)
                        .setContentText(messagebody);

        Intent result=new Intent(click);
        result.putExtra("message",datamessage);
        PendingIntent resultpending=
                PendingIntent.getActivity(this,0,result,PendingIntent.FLAG_UPDATE_CURRENT);
        mbuilder.setContentIntent(resultpending);

        int notificationid=(int)System.currentTimeMillis();
        NotificationManager notmanager=
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notmanager.notify(notificationid,mbuilder.build());
    }

}
