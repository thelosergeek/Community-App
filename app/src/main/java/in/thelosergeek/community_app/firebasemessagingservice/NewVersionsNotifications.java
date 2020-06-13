package in.thelosergeek.community_app.firebasemessagingservice;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NewVersionsNotifications extends ContextWrapper {

    private static final String ID = "some_id";
    private static final String NAME = "community_app";

    private NotificationManager notificationManager;


    public NewVersionsNotifications(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            createChannel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(ID, NAME, notificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getNotificationManager().createNotificationChannel(notificationChannel);
    }
    public NotificationManager getNotificationManager(){
        if(notificationManager == null){
            notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        }
        return notificationManager;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getONotification(String title, String body, PendingIntent pendingIntent, String icon){
        return  new Notification.Builder(getApplicationContext(),ID)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSmallIcon(Integer.parseInt(icon));
    }
}
