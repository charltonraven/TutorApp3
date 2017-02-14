package movingforward.tutorapp3.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;

import movingforward.tutorapp3.Entities.Appointment;
import movingforward.tutorapp3.Entities.Teacher;
import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Find_Class.BySubjectFragmentOne;
import movingforward.tutorapp3.R;
import movingforward.tutorapp3.TutChat.ChatActivity;

public class Nav_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{

    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    NavigationView navigationView;
    User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav__main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        Intent navIntent = getIntent();
        User mUser = (User) navIntent.getSerializableExtra("mUser");

        TextView tvType;
        TextView tvEmail;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        String UserType = mUser.getPermission().toString();
        String Email = mUser.getEmail();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        tvType = (TextView) hView.findViewById(R.id.tvType);
        tvEmail = (TextView) hView.findViewById(R.id.tvEmail);
        tvEmail.setText(Email);
        tvType.setText("Logged in as " + UserType.toUpperCase());

        switch (UserType)
        {
            case "Tutor":
                navigationView.getMenu().findItem(R.id.MyClasses).setVisible(false);
                navigationView.getMenu().findItem(R.id.MySessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.PostBulletin).setVisible(false);
                break;
            case "Student":
                navigationView.getMenu().findItem(R.id.MyClasses).setVisible(false);
                navigationView.getMenu().findItem(R.id.MySessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.PostBulletin).setVisible(false);
                navigationView.getMenu().findItem(R.id.TeacherSessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.StudentSessions).setVisible(false);

                break;
            case "Teacher":
                navigationView.getMenu().findItem(R.id.FindClass).setVisible(false);
                navigationView.getMenu().findItem(R.id.ListOfSavedTutors).setVisible(false);
                navigationView.getMenu().findItem(R.id.BulletinBoard).setVisible(false);
                navigationView.getMenu().findItem(R.id.StudentSessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.TeacherSessions).setVisible(false);

                break;
        }

        navigationView.setNavigationItemSelectedListener(this);
        drawer.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav__main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        switch (item.getItemId())
        {
            case R.id.sign_out_menu:
                //mFirebaseAuth.signOut();
                //Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                //mUsername = ANONYMOUS;
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.FindClass)
        {
            BySubjectFragmentOne subjectFragmentOne = new BySubjectFragmentOne();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, subjectFragmentOne, subjectFragmentOne.getTag()).commit();
        }
        else if (id == R.id.ListOfSavedTutors)
        {
            Intent chatIntent = new Intent(this, ChatActivity.class);
            chatIntent.putExtra("mUser", mUser);
            startActivity(chatIntent);
        }
        else if (id == R.id.BulletinBoard)
        {
        }
        else if (id == R.id.StudentSessions)
        {
        }
        else if (id == R.id.TeacherSessions)
        {


        }
        else if (id == R.id.MySessions)
        {


        }
        else if (id == R.id.MyClasses)
        {


        }
        else if (id == R.id.PostBulletin)
        {


        }
        else if (id == R.id.AppointTutor)
        {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        this.yearFinal = year;
        this.monthFinal = month + 1;
        this.dayFinal = day;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(Nav_MainActivity.this, AlertDialog.THEME_HOLO_DARK, Nav_MainActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {

        this.hourFinal = hour;
        this.minuteFinal = minute;
        String readthis = "year: " + yearFinal + "\n" +
                "month: " + monthFinal + "\n" +
                "day: " + dayFinal + "\n" +
                "hour: " + hourFinal + "\n" +
                "minute: " + minuteFinal;

        Appointment appointment = new Appointment("TestEmail@g.fmarion.edu", "Help with classes", monthFinal, dayFinal, yearFinal, hourFinal, minuteFinal);
        AppointmentTask appointmentTask = new AppointmentTask(this, appointment);
        appointmentTask.execute();


    }

    public static class AppointmentTask extends AsyncTask<String, Void, String>
    {
        Appointment appointment;
        AlertDialog.Builder RegorTry;
        AlertDialog alertDialog;
        Context context;

        AppointmentTask(Context context, Appointment appointment)
        {
            this.context = context;
            this.appointment = appointment;
        }

        @Override
        protected String doInBackground(String... params)
        {
            String login_url = "http://10.10.103.185/insertAppointment.php";
            String month = Integer.toString(appointment.month);
            String day = Integer.toString(appointment.day);
            String year = Integer.toString(appointment.year);
            String hour = Integer.toString(appointment.hour);
            String minute = Integer.toString(appointment.minute);

            try
            {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(appointment.EmailAddress, "UTF-8") + "&" + URLEncoder.encode("subject", "UTF-8") + "=" + URLEncoder.encode(appointment.Subject, "UTF-8")
                        + "&" + URLEncoder.encode("month", "UTF-8") + "=" + URLEncoder.encode(month, "UTF-8")
                        + "&" + URLEncoder.encode("day", "UTF-8") + "=" + URLEncoder.encode(day, "UTF-8")
                        + "&" + URLEncoder.encode("year", "UTF-8") + "=" + URLEncoder.encode(year, "UTF-8")
                        + "&" + URLEncoder.encode("hour", "UTF-8") + "=" + URLEncoder.encode(hour, "UTF-8")
                        + "&" + URLEncoder.encode("minute", "UTF-8") + "=" + URLEncoder.encode(minute, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String result = "";
                String line;

                while ((line = bufferedReader.readLine()) != null)
                {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();


                return result;


            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
        }
    }
}
