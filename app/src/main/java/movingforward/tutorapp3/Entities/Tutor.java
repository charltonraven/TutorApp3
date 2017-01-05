package movingforward.tutorapp3.Entities;

/**
 * Created by raven on 12/28/2016.
 */

public class Tutor {


    public String Email;
    public String FirstName;
    public String LastName;
    public String Classes;
    public String boolTutor;


    public String getBoolTutor() {
        return boolTutor;
    }

    public void setBoolTutor(String boolTutor) {
        this.boolTutor = boolTutor;
    }

    public String getClasses() {
        return Classes;
    }

    public void setClasses(String classes) {
        Classes = classes;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public Tutor(String boolTutor, String classes, String email, String firstName, String lastName) {
        this.boolTutor = boolTutor;
        Classes = classes;
        Email = email;
        FirstName = firstName;
        LastName = lastName;
    }



}
