package movingforward.tutorapp3.Entities.class_Helper;

/**
 * Created by raven on 12/30/2016.
 */

public class getImageText {


    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public int getClassImage() {
        return ClassImage;
    }

    public void setClassImage(int classImage) {
        ClassImage = classImage;
    }

    public String ClassName;
    public int ClassImage;


    public getImageText(String className, int classImage) {
        ClassName = className;
        ClassImage = classImage;
    }


}
