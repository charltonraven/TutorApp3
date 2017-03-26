package movingforward.tutorapp3.TutChat;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Jeebus Prime on 2/4/2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "FCMInitService";
    private static final String FRIENDLY_ENGAGE_TOPIC = "friendly_engage";

    @Override
    public void onTokenRefresh() {

        String fcmToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "FCM Device Token:" + fcmToken);
        //Save or send FCM registration token
        sendRegistrationToServer(fcmToken);
        // Once a token is generated, we subscribe to topic.
        FirebaseMessaging.getInstance().subscribeToTopic(FRIENDLY_ENGAGE_TOPIC);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}