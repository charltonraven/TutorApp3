package movingforward.tutorapp3.TutChat;

import com.google.firebase.messaging.FirebaseMessagingService;

/**
 * Created by raven on 2/21/2017.
 */

public class MessageRecieved extends FirebaseMessagingService {

    public MessageRecieved() {
        super();
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
