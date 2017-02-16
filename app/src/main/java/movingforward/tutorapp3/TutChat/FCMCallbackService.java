package movingforward.tutorapp3.TutChat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import movingforward.tutorapp3.Activities.Nav_MainActivity;
import movingforward.tutorapp3.R;

/**
 * Created by Jeebus Prime on 2/4/2017.
 */

public class FCMCallbackService extends FirebaseMessagingService {
    private static final String TAG = "FCMCallbackService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From:" + remoteMessage.getFrom());
        Log.d(TAG, "Message Body:" + remoteMessage.getNotification().getBody());
        sendNotification(remoteMessage.getNotification());
    }

    private void sendNotification(RemoteMessage.Notification notification) {
        int color = getResources().getColor(R.color.colorPrimary);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Nav_MainActivity was supposed to point to some 'MainActivity'
        Intent intent = new Intent(this, Nav_MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                //Art is a placeholder here
                .setSmallIcon(R.drawable.art)
                .setColor(color)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notification.getBody()))
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}