package movingforward.tutorapp3.Entities;

/**
 * Created by Jeebus Prime on 10/5/2016.
 */

public class Teacher
{
    private String name;
    private String email;
    private boolean admin;

    public Teacher(String name, String email)
    {
        this.name = name;
        this.email = email;
        admin = false;
    }

    public Teacher(String name, String email, boolean admin) {
        this.name = name;
        this.email = email;
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
