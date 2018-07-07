package com.tbm.dogs.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tbm.dogs.R;
import com.tbm.dogs.activities.main.Main;
import com.tbm.dogs.activities.viecdangco.ViecDangCo;

public class MyFirebaseMessaging extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification());
    }

    private void showNotification(RemoteMessage.Notification notification) {
        Intent it = new Intent(this, Main.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,it,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
        // notify to activity
        Intent callingIntent = new Intent(getApplicationContext(), ViecDangCo.class);
        callingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callingIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        String value = notification.getBody();
        callingIntent.putExtra("data", value);
        getApplicationContext().startActivity(callingIntent);
    }
}
