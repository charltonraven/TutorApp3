package movingforward.tutorapp3.Entities;

/**
 * Created by raven on 12/28/2016.
 */

public class Tutor {

    private String email;
    private Role permission;
    private String password="";
    public String FirstName;
    public String LastName;
    public String Classes;
    public String boolTutor;





    public Tutor(String email, String password){
        this.email=email;
        this.password=password;
        this.permission=Role.Tutor;
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
