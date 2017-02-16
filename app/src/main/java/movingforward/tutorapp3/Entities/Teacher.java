package movingforward.tutorapp3.Entities;

import java.io.Serializable;

/**
 * Created by Jeebus Prime on 10/5/2016.
 */

public class Teacher {
    private String name;
    private String email;
    private Role permission;
    private String password;
    private boolean admin;

    public Teacher(String email, String password){
        this.email=email;
      //  this.password=password;
        this.permission=Role.Teacher;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getPermission() {
        return permission;
    }

    public void setPermission(Role permission) {
        this.permission = permission;
    }

}
