package movingforward.tutorapp3.Entities;

import java.util.ArrayList;

/**
 * Created by Jeebus Prime on 10/5/2016.
 */

public class Student
{


    private String email;
    private Role permission;
    private String password="";
    private String firstName;
    private String lastName;
    private String classification;
    private String major;
    private boolean registered;
    private boolean tutor;
    private ArrayList<String> classes;


    public Student(String firstName, String lastName, boolean tutor){
        this.firstName = firstName;
        this.lastName = lastName;
        this.tutor = tutor;
    }


    public Student(String email, String firstName, String lastName, String major)
    {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        registered = false;
        tutor = false;
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




    public ArrayList<String> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<String> classes) {
        this.classes = classes;
    }


}

