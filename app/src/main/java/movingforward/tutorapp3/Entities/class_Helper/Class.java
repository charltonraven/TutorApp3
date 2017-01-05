package movingforward.tutorapp3.Entities.class_Helper;

/**
 * Created by raven on 12/28/2016.
 */

public class Class {

    public String Department;
    public String CourseNumber;
    public String SectionNumber;
    public String TeacherEmail;

    public Class(String courseNumber, String department, String sectionNumber, String teacherEmail) {
        CourseNumber = courseNumber;
        Department = department;
        SectionNumber = sectionNumber;
        TeacherEmail = teacherEmail;
    }
    public Class(String courseNumber, String department) {
        CourseNumber = courseNumber;
        Department = department;
    }
    public String getCourseNumber() {
        return CourseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        CourseNumber = courseNumber;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getSectionNumber() {
        return SectionNumber;
    }

    public void setSectionNumber(String sectionNumber) {
        SectionNumber = sectionNumber;
    }

    public String getTeacherEmail() {
        return TeacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        TeacherEmail = teacherEmail;
    }


}
