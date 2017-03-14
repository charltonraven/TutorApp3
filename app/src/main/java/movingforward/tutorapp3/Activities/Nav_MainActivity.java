package movingforward.tutorapp3.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import movingforward.tutorapp3.Entities.Role;
import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Find_Class.BySubjectFragmentOne;
import movingforward.tutorapp3.R;
import movingforward.tutorapp3.Sessions.Sessions;

import static movingforward.tutorapp3.Find_Class.BySubjectFragmentOne.newInstance;
import static movingforward.tutorapp3.R.id.TutorSessions;
//import static movingforward.tutorapp3.Sessions.Sessions.newInstance;

public class Nav_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Sessions.OnFragmentInteractionListener
{

    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    NavigationView navigationView;
    User mUser;
    User nUser;
    String mEmail;
    String mPassword;


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
        mUser = (User) navIntent.getSerializableExtra("mUser");
        mUser.setEmail(mUser.getEmail().trim());

        TextView tvType;
        TextView tvEmail;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        String UserType = mUser.getPermission().toString();
        mEmail = mUser.getEmail();
        mPassword = mUser.getPassword();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        tvType = (TextView) hView.findViewById(R.id.tvType);
        tvEmail = (TextView) hView.findViewById(R.id.tvEmail);
        tvEmail.setText(mEmail);
        tvType.setText("Logged in as " + UserType.toUpperCase());

        switch (mUser.getPermission().toString())
        {
            case "Tutor":
                navigationView.getMenu().findItem(R.id.MyClasses).setVisible(false);
                navigationView.getMenu().findItem(R.id.MySessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.PostBulletin).setVisible(false);
                mUser.setPermission(Role.Tutor);
                break;
            case "Student":
                navigationView.getMenu().findItem(R.id.MyClasses).setVisible(false);
                navigationView.getMenu().findItem(R.id.MySessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.PostBulletin).setVisible(false);
                navigationView.getMenu().findItem(R.id.TeacherSessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.StudentSessions).setVisible(false);
                mUser.setPermission(Role.Student);

                break;
            case "Teacher":
                navigationView.getMenu().findItem(R.id.FindClass).setVisible(false);
                navigationView.getMenu().findItem(TutorSessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.BulletinBoard).setVisible(false);
                navigationView.getMenu().findItem(R.id.StudentSessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.TeacherSessions).setVisible(false);
                mUser.setPermission(Role.Teacher);

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
        //User nUser = new User(mEmail, mPassword);
        //mUser = nUser;
        Bundle bundle=new Bundle();
        bundle.putSerializable("mUser",mUser);

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.FindClass)
        {

            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            BySubjectFragmentOne subjectFragmentOne = newInstance(mUser);
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, subjectFragmentOne, subjectFragmentOne.getTag()).commit();

        }
        else if (id == TutorSessions)
        {

           /* Intent chatIntent = new Intent(Nav_MainActivity.this, ChatActivity.class);
            chatIntent.putExtra("mUser", mUser);
            startActivity(chatIntent);*/

            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Sessions TutorSessions = Sessions.newInstance(mUser,"student");
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, TutorSessions, TutorSessions.getTag()).commit();


        }
        else if (id == R.id.BulletinBoard)
        {

        }
        else if (id == R.id.StudentSessions)
        {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Sessions StudentSessions = Sessions.newInstance(mUser,"tutor");
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, StudentSessions, StudentSessions.getTag()).commit();

        }

        else if (id == R.id.TeacherSessions)
        {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Sessions TeacherSession = Sessions.newInstance(mUser,"teacher");
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, TeacherSession, TeacherSession.getTag()).commit();


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
    public void onFragmentInteraction(Uri uri) {

    }

    public static class InserUserTask extends AsyncTask<String, Void, String>
    {


        @Override
        protected String doInBackground(String... params)
        {


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
