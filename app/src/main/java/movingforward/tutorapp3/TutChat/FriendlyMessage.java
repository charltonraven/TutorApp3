package movingforward.tutorapp3.TutChat;

/**
 * Created by Jeebus Prime on 2/9/2017.
 */

public class FriendlyMessage {

    private String id;
    private String text;
    private String name;
    private String photoUrl;
    private int tutor;

    public FriendlyMessage() {
    }

    public FriendlyMessage(String text, String name, String photoUrl, int tutor) {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.tutor = tutor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getTutor()
    {
        return tutor;
    }

    public void setTutor(int tutor)
    {
        this.tutor = tutor;
    }
}