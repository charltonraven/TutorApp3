package movingforward.tutorapp3.Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jeebus Prime on 10/5/2016.
 */

public class Student implements Serializable
{

    private String firstName;
    private String lastName;
    private String email;
    private String classification;
    private String major;
    private boolean registered;
    private boolean tutor;
    private ArrayList<String> classes;

    public Student(String email, String firstName, String lastName, String major)
    {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        registered = false;
        tutor = false;
    }

    public Student(String firstName, String lastName, String email, String classification, String major, boolean registered, boolean tutor, ArrayList<String> classes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.classification = classification;
        this.major = major;
        this.registered = registered;
        this.tutor = tutor;
        this.classes = classes;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public boolean isTutor() {
        return tutor;
    }

    public void setTutor(boolean tutor) {
        this.tutor = tutor;
    }

    public ArrayList<String> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<String> classes) {
        this.classes = classes;
    }


}
