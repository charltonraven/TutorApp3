package movingforward.tutorapp3.Entities;

/**
 * Created by Jeebus Prime on 10/5/2016.
 */

public class PotentialTeacher
{
    private String email;
    private int pin;

    public PotentialTeacher(String email, int pin) {
        this.email = email;
        this.pin = pin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}
