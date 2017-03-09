package movingforward.tutorapp3.Entities;



/**
 * Created by raven on 1/7/2017.
 */

public class ListItemHolder {

    public String ClassTutored;
    public String fName;
    public String lName;
    public String fullName;

    public ListItemHolder(String classTutored, String fName, String lName) {
        ClassTutored = classTutored;
        this.fName = fName;
        this.lName = lName;
    }
    public ListItemHolder(String Class, String name){
        this.ClassTutored=Class;
        this.fullName=name;
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