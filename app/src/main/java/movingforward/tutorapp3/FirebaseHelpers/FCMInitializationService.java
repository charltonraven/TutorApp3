package movingforward.tutorapp3.FirebaseHelpers;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Jeebus Prime on 2/4/2017.
 */

public class FCMInitializationService extends FirebaseInstanceIdService {
    private static final String TAG = "FCMInitService";

    @Override
    public void onTokenRefresh() {
        String fcmToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "FCM Device Token:" + fcmToken);
        //Save or send FCM registration token
    }
}