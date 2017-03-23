package movingforward.tutorapp3.Entities;

import java.io.Serializable;

/**
 * Created by raven on 2/9/2017.
 */

public class User implements Serializable {

    String email;
    String password;
    String firstName;
    String lastName;
    String ID;
    String major;
    String courses;
    int Registered;
    int tutor;
    Role permission;
    String firebaseToken = "";

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        ID = ID;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public int getRegistered() {
        return Registered;
    }

    public void setRegistered(int registered) {
        Registered = registered;
    }

    public int getTutor() {
        return tutor;
    }

    public void setTutor(int tutor) {
        this.tutor = tutor;
    }


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
    public User(String ID, String email, String firstName, String lastName, String major, String courses, int registered, int tutor, String password,Role permission){
        this.ID=ID;
        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
        this.major=major;
        this.courses=courses;
        this.Registered=registered;
        this.tutor=tutor;
        this.password=password;
        this.permission=permission;
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


