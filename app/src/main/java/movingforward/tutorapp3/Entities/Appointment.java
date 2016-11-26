package movingforward.tutorapp3.Entities;

/**
 * Created by raven on 11/15/2016.
 */

public class Appointment {


    public String FirstName, LastName, EmailAddress, Subject,ampm;
    public int day, month, year, hour, minute;

    public Appointment(String firstName, String lastName, String emailAddress, String subject, int month
            , int day, int year, int hour, int minute) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.EmailAddress = emailAddress;
        this.Subject = subject;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;

    }

    public Appointment(String emailAddress, String subject, int month
            , int day, int year, int hour, int minute) {

        this.EmailAddress = emailAddress;
        this.Subject = subject;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;

    }


}
