package movingforward.tutorapp3.Entities;

import java.io.Serializable;

/**
 * Created by raven on 2/9/2017.
 */

public class User implements Serializable {

    String email;
    String password;
    Role permission;

    String firebaseToken = "";

    public User(){};

    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String firebaseToken)
    {
        this.email = email;
        this.password = password;
        this.firebaseToken = firebaseToken;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setPermission(Role mRole)
    {
        permission = mRole;
    }

    public Role getPermission()
    {
        return permission;
    }

    public String getFirebaseToken()
    {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken)
    {
        this.firebaseToken = firebaseToken;
    }
}


