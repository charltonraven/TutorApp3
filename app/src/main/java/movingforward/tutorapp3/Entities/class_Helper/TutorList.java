package movingforward.tutorapp3.Entities.class_Helper;

/**
 * Created by raven on 1/7/2017.
 */

public class TutorList {

    public String ClassTutored;
    public String fName;
    public String lName;

    public TutorList(String classTutored, String fName, String lName) {
        ClassTutored = classTutored;
        this.fName = fName;
        this.lName = lName;
    }

    public String getClassTutored() {
        return ClassTutored;
    }

    public void setClassTutored(String classTutored) {
        ClassTutored = classTutored;
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
}