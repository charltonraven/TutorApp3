package movingforward.tutorapp3.ProjectHelpers;


/**
 * Created by raven on 1/7/2017.
 */

public class firstLastName {


    public String fName;
    public String lName;

    public boolean tutor;


    public firstLastName(String classTutored, String fName, String lName) {

        this.fName = fName;
        this.lName = lName;
    }

    public firstLastName(String firstName, String lastName) {
        this.fName = firstName;
        this.lName = lastName;
    }
    public firstLastName(String firstName, String lastName,boolean tutor) {
        this.fName = firstName;
        this.lName = lastName;
        this.tutor =tutor;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }


    public boolean isTutor() {
        return tutor;
    }

    public void setTutor(boolean tutor) {
        this.tutor = tutor;
    }

}

