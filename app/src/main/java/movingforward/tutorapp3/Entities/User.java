package movingforward.tutorapp3.Entities;

/**
 * Created by raven on 2/9/2017.
 */

public abstract class User {
    String email;
    String password;
    Role permission;
}
 enum Role{
     Tutor, Teacher, Student;

}

